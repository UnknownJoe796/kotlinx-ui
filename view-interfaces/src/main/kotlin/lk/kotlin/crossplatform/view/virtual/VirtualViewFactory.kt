package lk.kotlin.crossplatform.view.virtual

import lk.kotlin.crossplatform.view.*
import lk.kotlin.observable.list.ObservableList
import lk.kotlin.observable.property.MutableObservableProperty
import lk.kotlin.observable.property.ObservableProperty
import lk.kotlin.observable.property.StackObservableProperty
import java.util.*


object VirtualViewFactory : ViewFactory<View> {
    override fun body(text: ObservableProperty<String>): BodyView = BodyView(text)
    override fun button(image: ObservableProperty<Image?>, label: ObservableProperty<String?>, onClick: () -> Unit): ButtonView = ButtonView(image, label, onClick)
    override fun codeLayout(views: Array<out Pair<(ArrayList<Rectangle>) -> Rectangle, View>>): CodeLayoutView = CodeLayoutView(views)
    override fun datePicker(observable: MutableObservableProperty<Date>): DatePickerView = DatePickerView(observable)
    override fun dateTimePicker(observable: MutableObservableProperty<Date>): DateTimePickerView = DateTimePickerView(observable)
    override fun field(image: Image, hint: kotlin.String, help: kotlin.String, type: InputType, error: ObservableProperty<String>, text: MutableObservableProperty<String>): FieldView = FieldView(image, hint, help, type, error, text)
    override fun frame(views: Array<out Pair<Gravity, View>>): FrameView = FrameView(views)
    override fun <T> grid(minItemSize: Float, data: ObservableList<T>, itemToString: (T) -> kotlin.String, onBottom: () -> Unit, makeView: (type: kotlin.Int, obs: ObservableProperty<T>) -> View): GridView<T> = GridView(minItemSize, data, itemToString, onBottom, makeView)
    override fun header(text: ObservableProperty<String>): HeaderView = HeaderView(text)
    override fun horizontal(spacing: Float, views: Array<out Pair<Gravity, View>>): HorizontalView = HorizontalView(spacing, views)
    override fun image(minSize: Point, image: ObservableProperty<Image>): ImageView = ImageView(minSize, image)
    override fun <T> list(data: ObservableList<T>, itemToString: (T) -> kotlin.String, onBottom: () -> Unit, makeView: (type: kotlin.Int, obs: ObservableProperty<T>) -> View): ListView<T> = ListView(data, itemToString, onBottom, makeView)
    override fun listHeader(title: ObservableProperty<String>, subtitle: ObservableProperty<String?>, icon: ObservableProperty<Image?>, onClick: ObservableProperty<() -> Unit>): ListHeaderView = ListHeaderView(title, subtitle, icon, onClick)
    override fun listItem(title: ObservableProperty<String>, subtitle: ObservableProperty<String?>, icon: ObservableProperty<Image?>): ListItemView = ListItemView(title, subtitle, icon)
    override fun listItemClick(title: ObservableProperty<String>, subtitle: ObservableProperty<String?>, icon: ObservableProperty<Image?>, onClick: ObservableProperty<() -> Unit>): ListItemClickView = ListItemClickView(title, subtitle, icon, onClick)
    override fun listItemToggle(title: ObservableProperty<String>, subtitle: ObservableProperty<String?>, icon: ObservableProperty<Image?>, toggle: MutableObservableProperty<Boolean>): ListItemToggleView = ListItemToggleView(title, subtitle, icon, toggle)
    override fun margin(left: Float, top: Float, right: Float, bottom: Float, view: View): View = MarginView(left, top, right, bottom, view)
    override fun pages(pageGenerator: Array<out () -> View>): PagesView = PagesView(pageGenerator)
    override fun <T> picker(options: ObservableList<T>, selected: MutableObservableProperty<T>, makeView: (obs: ObservableProperty<T>) -> View): PickerView<T> = PickerView(options, selected, makeView)
    override fun progress(observable: ObservableProperty<Float>): ProgressView = ProgressView(observable)
    override fun refresh(contains: View, working:ObservableProperty<Boolean>, onRefresh: () -> Unit): RefreshView = RefreshView(contains, working, onRefresh)
    override fun scroll(view: View): ScrollView = ScrollView(view)
    override fun slider(steps: kotlin.Int, observable: MutableObservableProperty<Float>): SliderView = SliderView(steps, observable)
    override fun subheader(text: ObservableProperty<String>): SubheaderView = SubheaderView(text)
    override fun swap(view: ObservableProperty<Pair<View, Animation>>): SwapView = SwapView(view)
    override fun tabs(options: ObservableList<TabItem>, selected: MutableObservableProperty<TabItem>): TabsView = TabsView(options, selected)
    override fun timePicker(observable: MutableObservableProperty<Date>): TimePickerView = TimePickerView(observable)
    override fun toggle(observable: MutableObservableProperty<Boolean>): ToggleView = ToggleView(observable)
    override fun vertical(spacing: Float, views: Array<out Pair<Gravity, View>>): VerticalView = VerticalView(spacing, views)
    override fun window(stack: StackObservableProperty<() -> View>, tabs: List<Pair<TabItem, () -> View>>, actions: ObservableList<TabItem>): WindowView = WindowView(stack, tabs, actions)
    override fun work(): WorkView = WorkView()

}