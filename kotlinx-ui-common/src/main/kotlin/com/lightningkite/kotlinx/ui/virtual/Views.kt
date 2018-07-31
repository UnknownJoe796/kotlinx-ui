package com.lightningkite.kotlinx.ui.virtual

import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.ui.*


class BodyView(var text: ObservableProperty<String>) : View()


class ButtonView(var image: ObservableProperty<Image?>, var label: ObservableProperty<String?>, var onClick: () -> Unit) : View()


class CodeLayoutView(var views: Array<out Pair<(MutableList<Rectangle>) -> Rectangle, View>>) : ContainerView() {
    override fun views() = views.map { it.second }
}


class DatePickerView(var observable: MutableObservableProperty<Date>) : View()


class DateTimePickerView(var observable: MutableObservableProperty<Date>) : View()


class FieldView(var image: Image, var hint: kotlin.String, var help: kotlin.String, var type: TextInputType, var error: ObservableProperty<String>, var text: MutableObservableProperty<String>) : View()



class GridView<T>(var minItemSize: Float, var data: ObservableList<T>, var itemToString: (T) -> kotlin.String, var onBottom: () -> Unit, var makeView: (type: kotlin.Int, obs: ObservableProperty<T>) -> View) : View()


class HeaderView(var text: ObservableProperty<String>) : View()


class ImageView(var minSize: Point, var image: ObservableProperty<Image>) : View()


class ListView<T>(var data: ObservableList<T>, var itemToString: (T) -> kotlin.String, var onBottom: () -> Unit, var makeView: (type: kotlin.Int, obs: ObservableProperty<T>) -> View) : View()


class ListHeaderView(var title: ObservableProperty<String>, var subtitle: ObservableProperty<String?>, var icon: ObservableProperty<Image?>, var onClick: ObservableProperty<() -> Unit>) : View()


class ListItemView(var title: ObservableProperty<String>, var subtitle: ObservableProperty<String?>, var icon: ObservableProperty<Image?>) : View()


class ListItemClickView(var title: ObservableProperty<String>, var subtitle: ObservableProperty<String?>, var icon: ObservableProperty<Image?>, var onClick: ObservableProperty<() -> Unit>) : View()


class ListItemToggleView(var title: ObservableProperty<String>, var subtitle: ObservableProperty<String?>, var icon: ObservableProperty<Image?>, var toggle: MutableObservableProperty<Boolean>) : View()


class MarginView(var left: Float, var top: Float, var right: Float, var bottom: Float, var view: View) : ContainerView() {
    override fun views(): List<View> = listOf(view)
}


class PagesView(var pageGenerator: Array<out () -> View>) : View()


class PickerView<T>(var options: ObservableList<T>, var selected: MutableObservableProperty<T>, var makeView: (obs: ObservableProperty<T>) -> View) : View()


class ProgressView(var observable: ObservableProperty<Float>) : View()


class RefreshView(var contains: View, var working: ObservableProperty<Boolean>, var onRefresh: () -> Unit) : ContainerView() {
    override fun views(): List<View> = listOf(contains)
}


class ScrollView(var view: View) : ContainerView() {
    override fun views(): List<View> = listOf(view)
}


class SliderView(var steps: kotlin.Int, var observable: MutableObservableProperty<Float>) : View()


class SubheaderView(var text: ObservableProperty<String>) : View()


class SwapView(var view: ObservableProperty<Pair<View, Animation>>) : View()


class TabsView(var options: ObservableList<TabItem>, var selected: MutableObservableProperty<TabItem>) : View()


class TimePickerView(var observable: MutableObservableProperty<Date>) : View()


class ToggleView(var observable: MutableObservableProperty<Boolean>) : View()


class WindowView(var stack: StackObservableProperty<() -> View>, var tabs: List<Pair<TabItem, () -> View>>, var actions: ObservableList<TabItem>) : View()


class WorkView() : View()