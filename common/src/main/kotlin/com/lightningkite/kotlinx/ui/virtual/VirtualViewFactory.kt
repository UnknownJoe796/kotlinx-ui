package com.lightningkite.kotlinx.ui.virtual

import com.lightningkite.kotlinx.ui.*
import com.lightningkite.kotlinx.observable.property.*
import com.lightningkite.kotlinx.observable.list.*
import com.lightningkite.kotlinx.locale.*

object VirtualViewFactory : ViewFactory<View> {
    override fun window(stack: StackObservableProperty<() -> View>, tabs: List<Pair<TabItem, () -> View>>, actions: ObservableList<Pair<TabItem, () -> Unit>>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pages(vararg pageGenerator: () -> View): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun tabs(options: ObservableList<TabItem>, selected: MutableObservableProperty<TabItem>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> list(data: ObservableList<T>, onBottom: () -> Unit, itemToType: (T) -> Int, makeView: (type: Int, obs: ObservableProperty<T>) -> View): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> grid(minItemSize: Float, data: ObservableList<T>, onBottom: () -> Unit, itemToType: (T) -> Int, makeView: (type: Int, obs: ObservableProperty<T>) -> View): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun header(text: ObservableProperty<String>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subheader(text: ObservableProperty<String>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun body(text: ObservableProperty<String>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun work(): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun progress(observable: ObservableProperty<Float>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun image(minSize: Point, image: ObservableProperty<Image>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun button(image: ObservableProperty<Image?>, label: ObservableProperty<String?>, onClick: () -> Unit): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> picker(options: ObservableList<T>, selected: MutableObservableProperty<T>, makeView: (obs: ObservableProperty<T>) -> View): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun field(image: Image, hint: String, help: String, type: InputType, error: ObservableProperty<String>, text: MutableObservableProperty<String>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun datePicker(observable: MutableObservableProperty<Date>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dateTimePicker(observable: MutableObservableProperty<Date>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun timePicker(observable: MutableObservableProperty<Date>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun slider(steps: Int, observable: MutableObservableProperty<Float>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toggle(observable: MutableObservableProperty<Boolean>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refresh(contains: View, working: ObservableProperty<Boolean>, onRefresh: () -> Unit): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scroll(view: View): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun margin(left: Float, top: Float, right: Float, bottom: Float, view: View): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun swap(view: ObservableProperty<Pair<View, Animation>>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun horizontal(spacing: Float, vararg views: Pair<Gravity, View>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun vertical(spacing: Float, vararg views: Pair<Gravity, View>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun frame(vararg views: Pair<Gravity, View>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun codeLayout(vararg views: Pair<(MutableList<Rectangle>) -> Rectangle, View>): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}