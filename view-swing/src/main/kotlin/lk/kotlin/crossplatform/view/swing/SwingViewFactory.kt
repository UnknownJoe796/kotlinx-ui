package lk.kotlin.crossplatform.view.swing

import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.observable.property.lifecycle.bind
import com.lightningkite.kotlinx.ui.*
import com.lightningkite.kotlinx.ui.Image
import com.lightningkite.kotlinx.ui.Point
import com.lightningkite.kotlinx.ui.helper.AnyLifecycles
import com.lightningkite.kotlinx.ui.helper.TreeObservableProperty
import java.awt.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.BadLocationException
import javax.swing.text.Document

var JComponent.lifecycle
    get() = AnyLifecycles.getOrPut(this) { TreeObservableProperty() }
    set(value) {
        AnyLifecycles[this] = value
    }

fun JComponent.lifecycleChildOf(parent: JComponent) {
    this.lifecycle.parent = parent.lifecycle
}

class SwingViewFactory() : ViewFactory<JComponent> {

    fun Align.toSwingHorizontal() = when (this) {
        Align.Start -> SwingConstants.LEADING
        Align.Center -> SwingConstants.CENTER
        Align.Fill -> SwingConstants.CENTER
        Align.End -> SwingConstants.TRAILING
    }

    fun Align.toSwingVertical() = when (this) {
        Align.Center -> SwingConstants.CENTER
        Align.Fill -> SwingConstants.CENTER
        Align.Start -> SwingConstants.TOP
        Align.End -> SwingConstants.BOTTOM
    }

    override fun header(text: ObservableProperty<String>, align: AlignPair): JComponent = JLabel().apply {
        this.horizontalAlignment = align.horizontal.toSwingHorizontal()
        this.verticalAlignment = align.vertical.toSwingVertical()
        lifecycle.bind(text) {
            this.text = "<html><h1>" + it.replace("\n", "<br>") + "</h1></html>"
        }
    }

    override fun subheader(text: ObservableProperty<String>, align: AlignPair): JComponent = JLabel().apply {
        this.horizontalAlignment = align.horizontal.toSwingHorizontal()
        this.verticalAlignment = align.vertical.toSwingVertical()
        lifecycle.bind(text) {
            this.text = "<html><h4>" + it.replace("\n", "<br>") + "</h4></html>"
        }
    }

    override fun body(text: ObservableProperty<String>, align: AlignPair): JComponent = JLabel().apply {
        this.horizontalAlignment = align.horizontal.toSwingHorizontal()
        this.verticalAlignment = align.vertical.toSwingVertical()
        lifecycle.bind(text) {
            this.text = "<html><p>" + it.replace("\n", "<br>") + "</p></html>"
        }
    }

    override fun horizontal(vararg views: Pair<PlacementPair, JComponent>): JComponent = JPanel().apply {
        layout = object : LayoutManager {
            override fun layoutContainer(parent: Container) {

                var current = 0
                val extraSpace = bounds.width - calcRequiredSpace(views, PlacementPair::horizontal, { it.preferredSize.width })

                for ((placement, view) in views) {
                    val myBounds = bounds

                    val width = calcSize(placement.horizontal, extraSpace, view.preferredSize.width)
                    val x = current
                    val height = calcSize(placement.vertical, myBounds.height, view.preferredSize.height)
                    val y = calcPos(placement.vertical, myBounds.height, height)
                    view.setBounds(
                            x,
                            y,
                            width,
                            height
                    )
                    current += width
                }
            }

            override fun preferredLayoutSize(parent: Container?): Dimension {
                return Dimension(
                        calcRequiredSpace(views, PlacementPair::horizontal, { it.preferredSize.width }),
                        views.asSequence().map { it.second.preferredSize.height }.max() ?: 0
                )
            }

            override fun minimumLayoutSize(parent: Container?): Dimension {
                return Dimension(
                        calcRequiredSpace(views, PlacementPair::horizontal, { it.minimumSize.width }),
                        views.asSequence().map { it.second.preferredSize.height }.max() ?: 0
                )
            }

            override fun addLayoutComponent(name: String?, comp: Component?) {
            }

            override fun removeLayoutComponent(comp: Component?) {
            }

        }
        for ((_, view) in views) {
            view.lifecycleChildOf(this)
            add(view)
        }
    }

    override fun vertical(vararg views: Pair<PlacementPair, JComponent>): JComponent = JPanel().apply {
        layout = object : LayoutManager {
            override fun layoutContainer(parent: Container) {

                var current = 0
                val extraSpace = bounds.height - calcRequiredSpace(views, PlacementPair::vertical, { it.preferredSize.height })

                for ((placement, view) in views) {
                    val myBounds = bounds

                    val width = calcSize(placement.horizontal, myBounds.width, view.preferredSize.width)
                    val x = calcPos(placement.horizontal, myBounds.width, width)
                    val height = calcSize(placement.vertical, extraSpace, view.preferredSize.height)
                    val y = current
                    view.setBounds(
                            x,
                            y,
                            width,
                            height
                    )
                    current += height
                }
            }

            override fun preferredLayoutSize(parent: Container?): Dimension {
                return Dimension(
                        views.asSequence().map { it.second.preferredSize.width }.max() ?: 0,
                        calcRequiredSpace(views, PlacementPair::vertical, { it.preferredSize.height })
                )
            }

            override fun minimumLayoutSize(parent: Container?): Dimension {
                return Dimension(
                        views.asSequence().map { it.second.preferredSize.width }.max() ?: 0,
                        calcRequiredSpace(views, PlacementPair::vertical, { it.minimumSize.height })
                )
            }

            override fun addLayoutComponent(name: String?, comp: Component?) {
            }

            override fun removeLayoutComponent(comp: Component?) {
            }

        }
        for ((_, view) in views) {
            view.lifecycleChildOf(this)
            add(view)
        }
    }

    override fun frame(vararg views: Pair<PlacementPair, JComponent>): JComponent = JPanel().apply {
        layout = object : LayoutManager {
            override fun layoutContainer(parent: Container) {
                for ((placement, view) in views) {
                    val myBounds = bounds

                    val width = calcSize(placement.horizontal, myBounds.width, view.preferredSize.width)
                    val x = calcPos(placement.horizontal, myBounds.width, width)
                    val height = calcSize(placement.vertical, myBounds.height, view.preferredSize.height)
                    val y = calcPos(placement.vertical, myBounds.height, height)
                    view.setBounds(
                            x,
                            y,
                            width,
                            height
                    )
                }
            }

            override fun preferredLayoutSize(parent: Container?): Dimension {
                return Dimension(
                        views.asSequence().map { it.second.preferredSize.width }.max() ?: 0,
                        views.asSequence().map { it.second.preferredSize.height }.max() ?: 0
                )
            }

            override fun minimumLayoutSize(parent: Container?): Dimension {
                return Dimension(
                        views.asSequence().map { it.second.minimumSize.width }.max() ?: 0,
                        views.asSequence().map { it.second.minimumSize.height }.max() ?: 0
                )
            }

            override fun addLayoutComponent(name: String?, comp: Component?) {
            }

            override fun removeLayoutComponent(comp: Component?) {
            }
        }
        for ((_, view) in views) {
            view.lifecycleChildOf(this)
            add(view)
        }
    }

    override fun window(
            stack: StackObservableProperty<() -> JComponent>,
            tabs: List<Pair<TabItem, () -> JComponent>>,
            actions: ObservableList<Pair<TabItem, () -> Unit>>
    ): JComponent = TODO()

    override fun pages(vararg pageGenerator: () -> JComponent): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun tabs(options: ObservableList<TabItem>, selected: MutableObservableProperty<TabItem>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> list(data: ObservableList<T>, onBottom: () -> Unit, itemToType: (T) -> Int, makeView: (type: Int, obs: ObservableProperty<T>) -> JComponent): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> grid(minItemSize: Float, data: ObservableList<T>, onBottom: () -> Unit, itemToType: (T) -> Int, makeView: (type: Int, obs: ObservableProperty<T>) -> JComponent): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun work(): JComponent = JProgressBar().apply {
        isIndeterminate = true
    }

    override fun progress(observable: ObservableProperty<Float>): JComponent = JProgressBar(0, 100).apply {
        lifecycle.bind(observable) {
            value = (it * 100).toInt()
        }
    }

    override fun image(minSize: Point, scaleType: ImageScaleType, image: ObservableProperty<Image>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun button(image: ObservableProperty<Image?>, label: ObservableProperty<String?>, onClick: () -> Unit): JComponent = JButton().apply {
        lifecycle.bind(label) {
            text = it
        }
        addActionListener {
            onClick.invoke()
        }
    }

    override fun <T> picker(
            options: ObservableList<T>,
            selected: MutableObservableProperty<T>,
            makeView: (obs: ObservableProperty<T>) -> JComponent
    ): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var Document.text: String
        get() = try {
            getText(0, length)
        } catch (e: BadLocationException) {
            ""
        }
        set(value) {
            remove(0, length)
            insertString(0, value, null)
        }

    private fun bindDocument(lifecycle: ObservableProperty<Boolean>, document: Document, text: MutableObservableProperty<String>) {
        lifecycle.bind(text) {
            if (document.text != it) {
                document.text = it
            }
        }
        document.addDocumentListener(object : DocumentListener {
            override fun changedUpdate(e: DocumentEvent?) = onChange()
            override fun insertUpdate(e: DocumentEvent?) = onChange()
            override fun removeUpdate(e: DocumentEvent?) = onChange()
            fun onChange() {
                val docText = document.text
                if (docText != text.value) {
                    text.value = docText
                }
            }
        })
    }

    override fun textField(
            image: Image,
            hint: String,
            help: String,
            type: TextInputType,
            error: ObservableProperty<String>,
            text: MutableObservableProperty<String>
    ): JComponent {
        return when (type) {
            TextInputType.Paragraph -> TODO()
            TextInputType.Name -> JTextField(30).apply {
                this.toolTipText = hint
                bindDocument(lifecycle, document, text)
            }
            TextInputType.Password -> JPasswordField(30).apply {
                this.toolTipText = hint
                bindDocument(lifecycle, document, text)
            }
            TextInputType.Sentence -> TODO()
            TextInputType.CapitalizedIdentifier -> TODO()
            TextInputType.URL -> TODO()
            TextInputType.Email -> TODO()
            TextInputType.Phone -> TODO()
            TextInputType.Address -> TODO()
            TextInputType.Integer -> TODO()
            TextInputType.Float -> TODO()
            TextInputType.PositiveInteger -> TODO()
            TextInputType.PositiveFloat -> TODO()
        }
    }

    override fun datePicker(observable: MutableObservableProperty<Date>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dateTimePicker(observable: MutableObservableProperty<Date>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun timePicker(observable: MutableObservableProperty<Date>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun slider(steps: Int, observable: MutableObservableProperty<Float>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toggle(observable: MutableObservableProperty<Boolean>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refresh(contains: JComponent, working: ObservableProperty<Boolean>, onRefresh: () -> Unit): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scroll(view: JComponent): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun margin(left: Float, top: Float, right: Float, bottom: Float, view: JComponent): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun swap(view: ObservableProperty<Pair<JComponent, Animation>>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun space(size: Point): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    inline fun calcRequiredSpace(
            views: Array<out Pair<PlacementPair, JComponent>>,
            getDimenPlacement: (PlacementPair) -> Placement,
            getDimen: (JComponent) -> Int
    ): Int {
        var max = 0
        for ((placement, view) in views) {
            val next = calcSize(getDimenPlacement(placement), 0, getDimen(view))
            max += next
        }
        return max
    }

    fun calcSize(
            p: Placement,
            fillDimen: Int,
            preferred: Int
    ) = if (p.align == Align.Fill) {
        (fillDimen * p.size).toInt().coerceAtLeast(preferred)
    } else if (p.size == 0f) {
        preferred
    } else {
        p.size.toInt()
    }

    fun calcPos(
            p: Placement,
            containerDimen: Int,
            size: Int
    ) = when (p.align) {
        Align.Start -> 0
        Align.Center,
        Align.Fill -> containerDimen / 2 - size / 2
        Align.End -> containerDimen - size
    }
}