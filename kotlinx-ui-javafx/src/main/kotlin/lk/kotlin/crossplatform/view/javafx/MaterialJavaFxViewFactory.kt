package lk.kotlin.crossplatform.view.javafx

import com.jfoenix.controls.*
import com.lightningkite.kotlinx.httpclient.HttpClient
import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.Time
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.list.mapping
import com.lightningkite.kotlinx.observable.property.*
import com.lightningkite.kotlinx.observable.property.lifecycle.bind
import com.lightningkite.kotlinx.ui.*
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.helper.*
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory
import javafx.application.Platform
import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyProperty
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.effect.DropShadow
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.web.WebView
import javafx.util.StringConverter
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

var Node.lifecycle
    get() = AnyLifecycles.getOrPut(this) { TreeObservableProperty() }
    set(value) {
        AnyLifecycles[this] = value
    }
private val desiredMargins = WeakHashMap<Node, Insets>()

data class MaterialJavaFxViewFactory(
        override val theme: Theme,
        override val colorSet: ColorSet = theme.main,
        val resourceFetcher: (String) -> InputStream,
        val scale: Double = 1.0
) : ViewFactory<Node> {

    init {
        HttpClient.resultThread = { Platform.runLater(it) }
    }

    override fun withColorSet(colorSet: ColorSet): ViewFactory<Node> = copy(colorSet = colorSet)

    val TextSize.javafx
        get() = when (this) {
            TextSize.Tiny -> 10.0 * scale
            TextSize.Body -> 14.0 * scale
            TextSize.Subheader -> 18.0 * scale
            TextSize.Header -> 24.0 * scale
        }

    fun <T> ObservableProperty<Boolean>.bindBidirectional(kotlinx: MutableObservableProperty<T>, property: Property<T>) {
        bind(kotlinx) {
            if (it != property.value) {
                property.value = it
            }
        }
        property.addListener { observable, oldValue, newValue ->
            if (newValue != kotlinx.value) {
                kotlinx.value = newValue
            }
        }
    }

    fun <T> ObservableProperty<Boolean>.bindBidirectional(kotlinx: MutableObservableProperty<T>, property: ReadOnlyProperty<T>, write: (T) -> Unit) {
        bind(kotlinx) {
            if (it != property.value) {
                write(it)
            }
        }
        property.addListener { observable, oldValue, newValue ->
            if (newValue != kotlinx.value) {
                kotlinx.value = newValue
            }
        }
    }


    init {
        SvgImageLoaderFactory.install()
//        SvgImageLoaderFactory.install(PrimitiveDimensionProvider());
    }

    override fun text(
            text: ObservableProperty<String>,
            style: Importance,
            size: TextSize,
            align: AlignPair
    ): Node = Label().apply {
        font = Font.font(size.javafx)
        textFill = when (style) {
            Importance.Low -> colorSet.foregroundDisabled.toJavaFX()
            Importance.Normal -> colorSet.foreground.toJavaFX()
            Importance.High -> colorSet.foregroundHighlighted.toJavaFX()
            Importance.Danger -> Color.red.toJavaFX()
        }
        alignment = align.javafx
        lifecycle.bind(text) {
            this.text = it
        }
    }

    override fun horizontal(vararg views: Pair<PlacementPair, Node>) = HBox().apply {
        val parent = this
        for ((placement, view) in views) {
            children += frame(PlacementPair(Placement.fill, placement.vertical) to view).apply {
                this.lifecycle.parent = parent.lifecycle
                maxHeight = Double.MAX_VALUE
                if (placement.horizontal.align == Align.Fill) {
                    maxWidth = Double.MAX_VALUE
                    HBox.setHgrow(this, Priority.ALWAYS)
                }
            }
        }
    }

    override fun vertical(vararg views: Pair<PlacementPair, Node>) = VBox().apply {
        val parent = this
        for ((placement, view) in views) {
            children += frame(PlacementPair(placement.horizontal, Placement.fill) to view).apply {
                this.lifecycle.parent = parent.lifecycle
                maxWidth = Double.MAX_VALUE
                if (placement.vertical.align == Align.Fill) {
                    maxHeight = Double.MAX_VALUE
                    VBox.setVgrow(this, Priority.ALWAYS)
                }
            }
        }
    }

    override fun frame(vararg views: Pair<PlacementPair, Node>) = StackPane().apply {
        val parent = this
        for ((placement, view) in views) {
            children += view.apply {
                this.lifecycle.parent = parent.lifecycle
                StackPane.setAlignment(view, placement.alignPair.javafx)
                desiredMargins[this]?.let { StackPane.setMargin(this, it) }
                (this as? Region)?.let {
                    if (placement.vertical.align == Align.Fill) {
                        it.maxHeight = Double.MAX_VALUE
                    }
                    if (placement.horizontal.align == Align.Fill) {
                        it.maxWidth = Double.MAX_VALUE
                    }
                }
            }
        }
    }

    override fun imageButton(
            image: ObservableProperty<Image>,
            label: ObservableProperty<String?>,
            importance: Importance,
            onClick: () -> Unit
    ) = JFXButton().apply {
        val parent = this
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
        graphic = image(image).apply {
            this.lifecycle.parent = parent.lifecycle
        }
        setOnAction {
            onClick.invoke()
        }
    }


    override fun <DEPENDENCY> window(
            dependency: DEPENDENCY,
            stack: StackObservableProperty<ViewGenerator<DEPENDENCY, Node>>,
            tabs: List<Pair<TabItem, ViewGenerator<DEPENDENCY, Node>>>,
            actions: ObservableList<Pair<TabItem, () -> Unit>>
    ): Node = defaultLargeWindow(dependency, stack, tabs, actions)

    override fun <DEPENDENCY> pages(
            dependency: DEPENDENCY,
            page: MutableObservableProperty<Int>,
            vararg pageGenerator: ViewGenerator<DEPENDENCY, Node>
    ): Node = defaultPages(dependency, page, *pageGenerator)

    override fun tabs(
            options: ObservableList<TabItem>,
            selected: MutableObservableProperty<TabItem>
    ) = JFXTabPane().apply {

        //TODO: Color
//        style = "-fx-background-color: ${colorSet.background.toWeb()}"

        options.mapping {
            Tab(
                    it.text,
                    image(
                            ConstantObservableProperty(it.image)
                    )
            )
        }.bindToJavaFX(lifecycle, tabs)

        lifecycle.bindBidirectional<Tab>(
                selected.transform(
                        mapper = { tabs[options.indexOf(it)] },
                        reverseMapper = { options[tabs.indexOf(it)] }
                ),
                this.selectionModel.selectedItemProperty()
        ) { it: Tab ->
            selectionModel.select(it)
        }
    }

    override fun <T> list(
            data: ObservableList<T>,
            onBottom: () -> Unit,
            makeView: (obs: ObservableProperty<T>) -> Node
    ) = listVerticalImpl(data, onBottom, makeView)

    private data class RecycleableView<T>(
            val node: Node,
            val observable: MutableObservableProperty<T>
    )

    fun <T> listVerticalImpl(
            data: ObservableList<T>,
            onBottom: () -> Unit,
            makeView: (obs: ObservableProperty<T>) -> Node
    ) = scroll(VBox().apply {
        val parent = this
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE
        background = Background(BackgroundFill(colorSet.background.toJavaFX(), CornerRadii.EMPTY, Insets.EMPTY))

        var usedViews = ArrayList<RecycleableView<T>>()
        var unusedViews = ArrayList<RecycleableView<T>>()
        fun reqView(forItem: T): RecycleableView<T> {
            if (unusedViews.isNotEmpty()) {
                return unusedViews.removeAt(unusedViews.lastIndex).also {
                    it.observable.value = forItem
                    usedViews.add(it)
                    children.add(it.node)
                    it.node.lifecycle.parent = parent.lifecycle
                }
            }
            val obs = StandardObservableProperty<T>(forItem)
            val view = makeView(obs)
            view.lifecycle.parent = parent.lifecycle
            (view as? Region)?.maxWidth = Double.MAX_VALUE
            val sum = RecycleableView(view, obs)
            usedViews.add(sum)
            children.add(view)
            return sum
        }

        fun recycleView(v: RecycleableView<T>) {
            lifecycle.parent = null
            usedViews.remove(v)
            unusedViews.add(v)
            children.remove(v.node)
        }

        fun recycleAll() {
            for (item in usedViews) {
                lifecycle.parent = null
            }
            unusedViews = usedViews
            usedViews = ArrayList()
            children.clear()
        }

        lifecycle.bind(data.onUpdate) {
            recycleAll()
            it.forEach {
                reqView(it)
            }
        }
    })

    fun <T> listViewImpl(
            data: ObservableList<T>,
            onBottom: () -> Unit,
            makeView: (obs: ObservableProperty<T>) -> Node
    ) = JFXListView<T>().apply {
        items = data.asJavaFX(lifecycle)
        background = Background.EMPTY

        selectionModel = NoSelectionModel()

        setCellFactory {
            object : ListCell<T>() {
                val obs = StandardObservableProperty<T>(data.firstOrNull() as T)
                val mainView = makeView(obs)

                init {
                    mainView.lifecycle.parent = this@apply.lifecycle
                    contentDisplay = ContentDisplay.GRAPHIC_ONLY
                    background = Background.EMPTY
                    (mainView as? Region)?.maxWidth = Double.MAX_VALUE
                    graphic = mainView
                }

                override fun updateItem(item: T?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) {
                        graphic = null
                    } else {
                        graphic = mainView
                        obs.value = item as T
                    }
                }
            }
        }
    }

    override fun work(view: Node, isWorking: ObservableProperty<Boolean>): Node {
        val spinner = JFXSpinner().apply {
            style = "-fx-stroke: ${colorSet.foreground.toWeb()}"
            isVisible = true
            minWidth = 30.0 * scale
            minHeight = 30.0 * scale
            prefWidth = 30.0 * scale
            prefHeight = 30.0 * scale
        }
        return swap(
                view = isWorking.transform {
                    val nextView = if (it) spinner else view
                    nextView to Animation.Fade
                }
        )
    }

    override fun progress(view: Node, progress: ObservableProperty<Float>): Node {
        val bar = JFXProgressBar().apply {
            style = "-fx-stroke: ${colorSet.foreground.toWeb()}"
            lifecycle.bind(progress) {
                this.progress = it.toDouble()
            }
        }
        return swap(
                view = progress.transform {
                    val nextView = if (it == 1f) view else bar
                    nextView to Animation.Fade
                }
        )
    }

    override fun image(image: ObservableProperty<Image>) = ImageView().apply {
        lifecycle.bind(image) {
            Thread {
                val javafx = when (it) {
                    is Image.Bundled -> javafx.scene.image.Image(resourceFetcher.invoke(it.identifier))
                    is Image.Url -> javafx.scene.image.Image(it.url, true)
                    is Image.File -> javafx.scene.image.Image(FileInputStream(File(it.filePath)))
                    is Image.EmbeddedSVG -> javafx.scene.image.Image(ByteArrayInputStream(it.data.toByteArray()))
                }
                Platform.runLater {
                    it.size?.x?.times(scale)?.let { this.fitWidth = it }
                    it.size?.y?.times(scale)?.let { this.fitHeight = it }
                    //TODO: Scale type
                    this.image = javafx
                }
            }.start()
        }
    }

    override fun button(
            label: ObservableProperty<String>,
            image: ObservableProperty<Image?>,
            importance: Importance,
            onClick: () -> Unit
    ) = JFXButton().apply {
        this.buttonType = if (importance == Importance.Low) JFXButton.ButtonType.FLAT else JFXButton.ButtonType.RAISED

        val colorSet = theme.importance(importance)

        background = Background(BackgroundFill(colorSet.background.toJavaFX(), CornerRadii.EMPTY, Insets.EMPTY))
        textFill = colorSet.foreground.toJavaFX()
        font = Font.font(TextSize.Body.javafx)
        lifecycle.bind(label) {
            text = it
        }
        setOnAction {
            onClick.invoke()
        }
    }

    override fun entryContext(
            label: String,
            help: String?,
            icon: Image?,
            feedback: ObservableProperty<Pair<Importance, String>?>,
            field: Node
    ): Node = defaultEntryContext(label, help, icon, feedback, field)

    fun toggleButton(
            image: ObservableProperty<Image?>,
            label: ObservableProperty<String?>,
            value: MutableObservableProperty<Boolean>
    ) = JFXButton().apply {
        this.buttonType = JFXButton.ButtonType.RAISED
        textFill = colorSet.foreground.toJavaFX()
        lifecycle.bind(label) {
            text = it
        }
        lifecycle.bind(value) {
            background = if (it) {
                Background(BackgroundFill(colorSet.backgroundHighlighted.toJavaFX(), CornerRadii.EMPTY, Insets.EMPTY))
            } else {
                Background(BackgroundFill(colorSet.background.toJavaFX(), CornerRadii.EMPTY, Insets.EMPTY))
            }
        }
        setOnAction {
            value.value != value.value
        }
    }

    override fun <T> picker(
            options: ObservableList<T>,
            selected: MutableObservableProperty<T>,
            makeView: (obs: ObservableProperty<T>) -> Node
    ) = JFXComboBox<T>().apply {

        focusColor = colorSet.backgroundHighlighted.toJavaFX()
        unFocusColor = colorSet.background.toJavaFX()
        items = options.asJavaFX(lifecycle)

        setCellFactory {
            object : ListCell<T>() {
                val obs = StandardObservableProperty<T>(options.firstOrNull() as T)

                init {
                    children += makeView(obs)
                }

                override fun updateItem(item: T, empty: Boolean) {
                    obs.value = item
                }
            }
        }
    }

    override fun textField(text: MutableObservableProperty<String>, placeholder: String, type: TextInputType): Node =
            if (type == TextInputType.Password) JFXPasswordField().apply {
                font = Font.font(TextSize.Body.javafx)
                this.style = "-fx-text-fill: ${colorSet.foreground.toWeb()}"
                this.focusColor = colorSet.foregroundHighlighted.toJavaFX()
                this.unFocusColor = colorSet.foreground.toJavaFX()
                lifecycle.bindBidirectional(text, this.textProperty())
                this.isLabelFloat = true
            } else JFXTextField().apply {
                font = Font.font(TextSize.Body.javafx)
                this.style = "-fx-text-fill: ${colorSet.foreground.toWeb()}"
                this.focusColor = colorSet.foregroundHighlighted.toJavaFX()
                this.unFocusColor = colorSet.foreground.toJavaFX()
                lifecycle.bindBidirectional(text, this.textProperty())
                this.isLabelFloat = true
            }

    override fun textArea(text: MutableObservableProperty<String>, placeholder: String, type: TextInputType): Node = JFXTextArea().apply {
        font = Font.font(TextSize.Body.javafx)
        this.style = "-fx-text-fill: ${colorSet.foreground.toWeb()}"
        this.focusColor = colorSet.foregroundHighlighted.toJavaFX()
        this.unFocusColor = colorSet.foreground.toJavaFX()
        lifecycle.bindBidirectional(text, this.textProperty())
        this.isLabelFloat = true
        minHeight = scale * 100.0
    }

    override fun numberField(value: MutableObservableProperty<Number?>, placeholder: String, type: NumberInputType, decimalPlaces: Int): Node = JFXTextField().apply {
        font = Font.font(TextSize.Body.javafx)
        this.style = "-fx-text-fill: ${colorSet.foreground.toWeb()}"
        this.focusColor = colorSet.foregroundHighlighted.toJavaFX()
        this.unFocusColor = colorSet.foreground.toJavaFX()

        val compareVal = Math.pow(-decimalPlaces.toDouble(), 10.0) / 2
        val converter = object : StringConverter<Number>() {
            override fun toString(value: Number?): String = if (value == null) "" else DecimalFormat("#." + "#".repeat(decimalPlaces)).format(value)
            override fun fromString(string: String?): Number? = string?.toDoubleOrNull()
        }
        textFormatter = TextFormatter(converter)
        lifecycle.bind(value) {
            val backing = it
            val converted = converter.fromString(text)
            val different =
                    (backing == null && converted != null) ||
                            (backing != null && converted == null) ||
                            (backing != null && converted != null && backing.toDouble().minus(converted.toDouble())
                                    .let { Math.abs(it) > compareVal })
            if (different) {
                text = converter.toString(backing)
            }
        }
        textProperty().addListener { _, _, newValue ->
            val backing = value.value
            val converted = converter.fromString(newValue)
            val different =
                    (backing == null && converted != null) ||
                            (backing != null && converted == null) ||
                            (backing != null && converted != null && backing.toDouble().minus(converted.toDouble())
                                    .let { Math.abs(it) > compareVal })
            if (different) {
                value.value = converter.fromString(newValue)
            }
        }
        this.isLabelFloat = true
    }

    override fun datePicker(observable: MutableObservableProperty<Date>) = JFXDatePicker().apply {
        this.editor.font = Font.font(TextSize.Body.javafx)
        this.editor.style = "-fx-text-fill: ${colorSet.foreground.toWeb()}"
        this.defaultColor = colorSet.foreground.toJavaFX()
        this.value = LocalDate.ofEpochDay(observable.value.daysSinceEpoch.toLong())
        lifecycle.bindBidirectional<LocalDate>(
                kotlinx = observable.transform(
                        mapper = { LocalDate.ofEpochDay(it.daysSinceEpoch.toLong()) },
                        reverseMapper = { Date(it.toEpochDay().toInt()) }
                ),
                property = valueProperty()
        )
    }

    override fun dateTimePicker(observable: MutableObservableProperty<DateTime>): Node = horizontal(
            PlacementPair.topFill to datePicker(observable = observable.transform(
                    mapper = { it.date },
                    reverseMapper = { observable.value.copy(date = it) }
            )),
            PlacementPair.topFill to space(Point(8f, 8f)),
            PlacementPair.topFill to timePicker(observable = observable.transform(
                    mapper = { it.time },
                    reverseMapper = { observable.value.copy(time = it) }
            ))
    )

    override fun timePicker(observable: MutableObservableProperty<Time>) = JFXTimePicker().apply {
        this.editor.font = Font.font(TextSize.Body.javafx)
        this.editor.style = "-fx-text-fill: ${colorSet.foreground.toWeb()}"
        this.defaultColor = colorSet.foreground.toJavaFX()
        this.value = LocalTime.ofNanoOfDay(observable.value.millisecondsSinceMidnight.times(1000000L))
        lifecycle.bindBidirectional<LocalTime>(
                kotlinx = observable.transform(
                        mapper = { LocalTime.ofNanoOfDay(it.millisecondsSinceMidnight.times(1000000L)) },
                        reverseMapper = { Time(it.toNanoOfDay().div(1000000L).toInt()) }
                ),
                property = valueProperty()
        )
    }

    override fun slider(range: IntRange, observable: MutableObservableProperty<Int>) = JFXSlider().apply {
        min = range.start.toDouble()
        max = range.endInclusive.toDouble()
        lifecycle.bindBidirectional(observable.transform(
                mapper = { it as Number },
                reverseMapper = { it.toInt() }
        ), valueProperty())
    }

    override fun toggle(observable: MutableObservableProperty<Boolean>) = JFXCheckBox().apply {
        lifecycle.bindBidirectional(observable, selectedProperty())
    }

    override fun refresh(contains: Node, working: ObservableProperty<Boolean>, onRefresh: () -> Unit): Node = frame(
            PlacementPair.fillFill to contains,
            PlacementPair.topRight to work(space(Point(20f, 20f)), working)
    )

    override fun scroll(view: Node) = ScrollPane(view).apply {
        hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        isFitToWidth = true
        isFitToHeight = true
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE
        style = "-fx-background-color: transparent; -fx-background: transparent;"
        view.lifecycle.parent = this.lifecycle
    }

    override fun swap(view: ObservableProperty<Pair<Node, Animation>>) = StackPane().apply {
        val parent = this
        var currentView: Node? = null

        background = Background(BackgroundFill(colorSet.background.toJavaFX(), CornerRadii.EMPTY, Insets.EMPTY))

        val clipRect = Rectangle()
        layoutBoundsProperty().addListener { observable, oldValue, newValue ->
            clipRect.width = newValue.width
            clipRect.height = newValue.height
        }
        clip = clipRect

        lifecycle.bind(view) { (view, animation) ->
            Platform.runLater {
                val containerSize = Point(
                        width.toFloat(),
                        height.toFloat()
                )
                currentView?.let { old ->
                    animation.javaFxOut(old, containerSize).apply {
                        setOnFinished {
                            old.lifecycle.parent = null
                            children.remove(old)
                        }
                    }.play()
                }

                children += view.apply {
                    this.lifecycle.parent = parent.lifecycle
                    StackPane.setAlignment(view, AlignPair.CenterCenter.javafx)
                    desiredMargins[this]?.let { StackPane.setMargin(this, it) }
                    if (currentView != null) {
                        animation.javaFxIn(this, containerSize).play()
                    }

                }
                currentView = view
            }
        }
    }

    override fun space(size: Point) = Region().apply {
        prefWidth = size.x.times(scale)
        prefHeight = size.y.times(scale)
    }

    override fun web(content: ObservableProperty<String>) = WebView().apply {
        lifecycle.bind(content) {
            if (it.startsWith("http")) {
                engine.load(it)
            } else {
                engine.loadContent(it)
            }
        }
    }


    override fun Node.margin(left: Float, top: Float, right: Float, bottom: Float) = this.apply {
        desiredMargins[this] = Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble())
    }


    override fun Node.background(color: ObservableProperty<Color>) = this.apply {
        if (this is Region) {
            lifecycle.bind(color) {
                this.background = Background(BackgroundFill(it.toJavaFX(), CornerRadii.EMPTY, Insets.EMPTY))
            }
        }
    }

    override fun card(view: Node): Node = frame(PlacementPair.fillFill to view).apply {
        background = Background(BackgroundFill(colorSet.background.toJavaFX(), CornerRadii(4.0 * scale), Insets.EMPTY))
        effect = DropShadow(3.0 * scale, 0.0, 1.0 * scale, Color.black.copy(alpha = .5f).toJavaFX())
        padding = Insets(8.0 * scale)
    }

    override fun Node.alpha(alpha: ObservableProperty<Float>) = this.apply {
        lifecycle.bind(alpha) {
            this.opacity = it.toDouble()
            isVisible = it != 0f
        }
    }

    override fun Node.clickable(onClick: () -> Unit) = this.apply {
        setOnMouseClicked {
            onClick.invoke()
        }
    }


}