package lk.kotlin.crossplatform.view.swing

import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.ui.*
import java.util.*
import javax.swing.JComponent
import javax.swing.JLabel

class SwingViewGenerator() : ViewFactory<JComponent> {
    override fun window(
            stack: StackObservableProperty<() -> JComponent>,
            tabs: List<Pair<TabItem, () -> JComponent>>,
            actions: ObservableList<Pair<TabItem, () -> Unit>>
    ): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

    override fun header(text: ObservableProperty<String>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subheader(text: ObservableProperty<String>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun body(text: ObservableProperty<String>): JComponent = JLabel().apply {
        li
    }

    override fun work(): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun progress(observable: ObservableProperty<Float>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun image(minSize: Point, image: ObservableProperty<Image>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun button(image: ObservableProperty<Image?>, label: ObservableProperty<String?>, onClick: () -> Unit): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> picker(options: ObservableList<T>, selected: MutableObservableProperty<T>, makeView: (obs: ObservableProperty<T>) -> JComponent): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun field(image: Image, hint: String, help: String, type: InputType, error: ObservableProperty<String>, text: MutableObservableProperty<String>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun horizontal(spacing: Float, vararg views: Pair<Gravity, JComponent>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun vertical(spacing: Float, vararg views: Pair<Gravity, JComponent>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun frame(vararg views: Pair<Gravity, JComponent>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun codeLayout(vararg views: Pair<(ArrayList<Rectangle>) -> Rectangle, JComponent>): JComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}