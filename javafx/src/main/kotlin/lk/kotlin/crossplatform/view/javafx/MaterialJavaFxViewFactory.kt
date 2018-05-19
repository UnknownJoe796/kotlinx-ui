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
import com.lightningkite.kotlinx.ui.helper.AnyLifecycles
import com.lightningkite.kotlinx.ui.helper.TreeObservableProperty
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

var Node.lifecycle
    get() = AnyLifecycles.getOrPut(this) { TreeObservableProperty() }
    set(value) {
        AnyLifecycles[this] = value
    }

fun Node.lifecycleChildOf(parent: Node) {
    this.lifecycle.parent = parent.lifecycle
}

data class MaterialJavaFxViewFactory(
        val theme: Theme,
        val colorSet: ColorSet = theme.main,
        val resourceFetcher: (String) -> InputStream,
        val scale: Double = 1.0
) : ViewFactory<Node> {

    init {
        HttpClient.resultThread = { Platform.runLater(it) }
    }

    override fun withColorSet(colorSet: ColorSet): ViewFactory<Node> = copy(colorSet = colorSet)

    companion object {
        fun embeddedBackButton(color: Color): String = """<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
    <path d="M0 0h24v24H0z" fill="none"/>
    <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z" fill="${color.toAlphalessWeb()}"/>
</svg>
"""

        fun embeddedLeft(color: Color): String = """<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
    <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z" fill="${color.toAlphalessWeb()}"/>
    <path d="M0 0h24v24H0z" fill="none"/>
</svg>
"""

        fun embeddedRight(color: Color): String = """<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
    <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z" fill="${color.toAlphalessWeb()}"/>
    <path d="M0 0h24v24H0z" fill="none"/>
</svg>
"""
    }

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

    override fun text(text: ObservableProperty<String>, size: TextSize, align: AlignPair) = Label().apply {
        font = Font.font(size.javafx)
        textFill = colorSet.foreground.toJavaFX()
        alignment = align.javafx
        lifecycle.bind(text) {
            this.text = it
        }
    }

    override fun horizontal(vararg views: Pair<PlacementPair, Node>) = HBox().apply {
        val parent = this
        for ((placement, view) in views) {
            children += frame(PlacementPair(Placement.fill, placement.vertical) to view).apply {
                lifecycleChildOf(parent)
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
                lifecycleChildOf(parent)
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
                lifecycleChildOf(parent)
                StackPane.setAlignment(view, placement.alignPair.javafx)
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

    fun imageButton(
            image: ObservableProperty<Image>,
            onClick: () -> Unit
    ) = JFXButton().apply {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
        graphic = image(Point(32f, 32f), ImageScaleType.Fill, image)
        setOnAction {
            onClick.invoke()
        }
    }

    override fun window(
            stack: StackObservableProperty<ViewGenerator<Node>>,
            tabs: List<Pair<TabItem, ViewGenerator<Node>>>,
            actions: ObservableList<Pair<TabItem, () -> Unit>>
    ) = with(this.copy(colorSet = theme.bar)) {

        vertical(
                PlacementPair.topFill to background(
                        horizontal(
                                PlacementPair.centerLeft to alpha(
                                        imageButton(ConstantObservableProperty(Image.EmbeddedSVG(embeddedBackButton(colorSet.foreground))), { stack.popOrFalse() }),
                                        stack.transform { if (stack.stack.size > 1) 1f else 0f }
                                ),
                                PlacementPair.centerLeft to text(text = stack.transform { it.title }, size = TextSize.Header),
                                PlacementPair.fillFill to space(Point(5f, 5f)),
                                PlacementPair.centerRight to swap(actions.onUpdate.transform {
                                    val items = it.map {
                                        PlacementPair.centerCenter to button(it.first.text, it.first.image) { it.second.invoke() }
                                    }
                                    horizontal(
                                            *items.toTypedArray()
                                    ) to Animation.Fade
                                })
                        ),
                        ConstantObservableProperty(colorSet.background)
                ).apply {
                    effect = DropShadow(2.0 * scale, 0.0, 2.0 * scale, Color.black.toJavaFX())
                },

                PlacementPair.fillFill to if (tabs.isEmpty()) {
                    with(this@MaterialJavaFxViewFactory) {
                        background(
                                swap(stack.withAnimations().transform { it.first.generate() to it.second }),
                                ConstantObservableProperty(colorSet.background)
                        )
                    }
                } else {
                    horizontal(
                            PlacementPair.fillLeft to scroll(vertical(
                                    //TODO
                            )),
                            PlacementPair.fillFill to with(this@MaterialJavaFxViewFactory) {
                                background(
                                        swap(stack.withAnimations().transform { it.first.generate() to it.second }),
                                        ConstantObservableProperty(colorSet.background)
                                )
                            }
                    )
                }
        )
    }

    override fun pages(
            page: MutableObservableProperty<Int>,
            vararg pageGenerator: ViewGenerator<Node>
    ): Node = vertical(
            PlacementPair.fillFill to run {
                var previous = page.value
                swap(page.transform {
                    val anim = when {
                        page.value < previous -> Animation.Pop
                        page.value > previous -> Animation.Push
                        else -> Animation.Fade
                    }
                    previous = it
                    pageGenerator[it.coerceIn(pageGenerator.indices)].generate() to anim
                })
            },
            PlacementPair.bottomFill to frame(
                    PlacementPair.bottomLeft to imageButton(ConstantObservableProperty(Image.EmbeddedSVG(embeddedLeft(colorSet.foreground))), {
                        page.value = page.value.minus(1).coerceIn(pageGenerator.indices)
                    }),
                    PlacementPair.bottomCenter to text(text = page.transform { "${it + 1} / ${pageGenerator.size}" }, size = TextSize.Tiny),
                    PlacementPair.bottomRight to imageButton(ConstantObservableProperty(Image.EmbeddedSVG(embeddedRight(colorSet.foreground))), {
                        page.value = page.value.plus(1).coerceIn(pageGenerator.indices)
                    })
            )
    )

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
                            Point(20f, 20f),
                            ImageScaleType.Center,
                            ConstantObservableProperty(it.image)
                    )
            )
        }.bindToJavaFX(lifecycle, tabs)

        lifecycle.bindBidirectional<Tab>(
                selected.transform(
                        mapper = { tabs[options.indexOf(it)] },
                        reverseMapper = { options[tabs.indexOf(it)] }
                ),
                this.selectionModel.selectedItemProperty(),
                { it: Tab ->
                    selectionModel.select(it)
                }
        )
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
                    it.node.lifecycle.on()
                }
            }
            val obs = StandardObservableProperty<T>(forItem)
            val view = makeView(obs)
            (view as? Region)?.maxWidth = Double.MAX_VALUE
            view.lifecycleChildOf(this)
            val sum = RecycleableView(view, obs)
            usedViews.add(sum)
            children.add(view)
            return sum
        }

        fun recycleView(v: RecycleableView<T>) {
            v.node.lifecycle.off()
            usedViews.remove(v)
            unusedViews.add(v)
            children.remove(v.node)
        }

        fun recycleAll() {
            for (item in usedViews) {
                item.node.lifecycle.off()
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
                    mainView.lifecycleChildOf(this@apply)
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

    override fun work() = JFXSpinner().apply {
        style = "-fx-stroke: ${colorSet.foreground.toWeb()}"
        isVisible = true
        minWidth = 30.0 * scale
        minHeight = 30.0 * scale
        prefWidth = 30.0 * scale
        prefHeight = 30.0 * scale
    }

    override fun progress(observable: ObservableProperty<Float>) = JFXProgressBar().apply {
        style = "-fx-stroke: ${colorSet.foreground.toWeb()}"
        lifecycle.bind(observable) {
            progress = it.toDouble()
        }
    }

    override fun image(minSize: Point, scaleType: ImageScaleType, image: ObservableProperty<Image>) = ImageView().apply {
        this.fitWidth = minSize.x.times(scale)
        this.fitHeight = minSize.y.times(scale)
        lifecycle.bind(image) {
            Thread {
                val javafx = when (it) {
                    is Image.Bundled -> javafx.scene.image.Image(resourceFetcher.invoke(it.identifier))
                    is Image.Url -> javafx.scene.image.Image(it.url, true)
                    is Image.File -> javafx.scene.image.Image(FileInputStream(File(it.filePath)))
                    is Image.EmbeddedSVG -> javafx.scene.image.Image(ByteArrayInputStream(it.data.toByteArray()))
                }
                Platform.runLater {
                    this.image = javafx
                }
            }.start()
        }
    }

    override fun button(
            image: ObservableProperty<Image?>,
            label: ObservableProperty<String?>,
            onClick: () -> Unit
    ) = JFXButton().apply {
        this.buttonType = JFXButton.ButtonType.RAISED
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

    override fun toggleButton(
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

    override fun textField(
            image: Image,
            hint: String,
            help: String,
            type: TextInputType,
            error: ObservableProperty<String>,
            text: MutableObservableProperty<String>
    ): Node =
            if (type == TextInputType.Password) JFXTextField().apply {
                this.focusColor = colorSet.foregroundHighlighted.toJavaFX()
                this.unFocusColor = colorSet.foreground.toJavaFX()
                lifecycle.bindBidirectional(text, this.textProperty())
                this.isLabelFloat = true
                this.promptText = hint
            }
            else JFXPasswordField().apply {
                lifecycle.bindBidirectional(text, this.textProperty())
                this.isLabelFloat = true
                this.promptText = hint
            }


    override fun textArea(
            image: Image,
            hint: String,
            help: String,
            type: TextInputType,
            error: ObservableProperty<String>,
            text: MutableObservableProperty<String>
    ) = JFXTextArea().apply {
        lifecycle.bindBidirectional(text, this.textProperty())
        this.isLabelFloat = true
        this.promptText = hint
    }

    override fun numberField(
            image: Image,
            hint: String,
            help: String,
            type: NumberInputType,
            error: ObservableProperty<String>,
            decimalPlaces: Int,
            value: MutableObservableProperty<Number?>
    ) = JFXTextField().apply {
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
        this.promptText = hint
    }

    override fun datePicker(observable: MutableObservableProperty<Date>) = JFXDatePicker().apply {
        this.value = LocalDate.ofEpochDay(observable.value.daysSinceEpoch.toLong())
        lifecycle.bindBidirectional<LocalDate>(
                kotlinx = observable.transform(
                        mapper = { LocalDate.ofEpochDay(it.daysSinceEpoch.toLong()) },
                        reverseMapper = { Date(it.toEpochDay().toInt()) }
                ),
                property = valueProperty()
        )
    }

    override fun dateTimePicker(observable: MutableObservableProperty<DateTime>): Node = vertical(
            PlacementPair.topFill to datePicker(observable = observable.transform(
                    mapper = { it.date },
                    reverseMapper = { observable.value.copy(date = it) }
            )),
            PlacementPair.topFill to timePicker(observable = observable.transform(
                    mapper = { it.time },
                    reverseMapper = { observable.value.copy(time = it) }
            ))
    )

    override fun timePicker(observable: MutableObservableProperty<Time>) = JFXTimePicker().apply {
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
            PlacementPair.topRight to alpha(work(), working.transform { if (it) 1f else 0f })
    )

    override fun scroll(view: Node) = ScrollPane(view).apply {
        hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        isFitToWidth = true
        isFitToHeight = true
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE
        background = Background(BackgroundFill(colorSet.background.toJavaFX(), CornerRadii.EMPTY, Insets.EMPTY))
    }

    override fun margin(left: Float, top: Float, right: Float, bottom: Float, view: Node) = StackPane().apply {
        children += view
        StackPane.setMargin(view, Insets(top.times(scale), right.times(scale), bottom.times(scale), left.times(scale)))
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
            val containerSize = Point(
                    width.toFloat(),
                    height.toFloat()
            )
            currentView?.let { old ->
                animation.javaFxOut(old, containerSize).apply {
                    setOnFinished {
                        children.remove(old)
                    }
                }.play()
            }

            children += view.apply {
                lifecycleChildOf(parent)
                StackPane.setAlignment(view, AlignPair.CenterCenter.javafx)
                if (currentView != null) {
                    animation.javaFxIn(this, containerSize).play()
                }

            }
            currentView = view

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

    override fun background(view: Node, color: ObservableProperty<Color>) = view.apply {
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

    override fun alpha(view: Node, alpha: ObservableProperty<Float>) = view.apply {
        lifecycle.bind(alpha) {
            this.opacity = it.toDouble()
            isVisible = it != 0f
        }
    }

    override fun clickable(view: Node, onClick: () -> Unit) = view.apply {
        setOnMouseClicked {
            onClick.invoke()
        }
    }
}