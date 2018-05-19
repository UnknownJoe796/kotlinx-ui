package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.Time
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.ui.color.Color

interface ViewFactory<VIEW> {

    //Theme

    fun withColorSet(colorSet: ColorSet): ViewFactory<VIEW>

    //Navigation

    fun window(
            stack: StackObservableProperty<ViewGenerator<VIEW>>,
            tabs: List<Pair<TabItem, ViewGenerator<VIEW>>>,
            actions: ObservableList<Pair<TabItem, ()->Unit>>
    ): VIEW

    fun pages(
            page: MutableObservableProperty<Int>,
            vararg pageGenerator: ViewGenerator<VIEW>
    ): VIEW

    fun tabs(
            options: ObservableList<TabItem>,
            selected: MutableObservableProperty<TabItem>
    ): VIEW


    //Collection Views

    fun <T> list(
            data: ObservableList<T>,
            onBottom: () -> Unit,
            makeView: (obs: ObservableProperty<T>) -> VIEW
    ): VIEW

//    fun <T> grid(
//            minItemSize: Float = Float.MAX_VALUE,
//            data: ObservableList<T>,
//            onBottom: () -> Unit = {},
//            itemToType: (T) -> Int,
//            makeView: (type: Int, obs: ObservableProperty<T>) -> VIEW
//    ): VIEW
    

    //Display

    fun text(
            text: ObservableProperty<String>,
            size: TextSize,
            align: AlignPair = AlignPair.CenterLeft
    ): VIEW

    fun work(): VIEW

    fun progress(
            observable: ObservableProperty<Float>
    ): VIEW

    fun image(
            minSize: Point,
            scaleType: ImageScaleType,
            image: ObservableProperty<Image>
    ): VIEW

    fun web(
            content: ObservableProperty<String>
    ): VIEW


    //Controls

    fun button(
            image: ObservableProperty<Image?> = ConstantObservableProperty(null),
            label: ObservableProperty<String?> = ConstantObservableProperty(null),
            onClick:()->Unit
    ): VIEW

    fun <T> picker(
            options: ObservableList<T>,
            selected: MutableObservableProperty<T>,
            makeView: (obs: ObservableProperty<T>) -> VIEW
    ): VIEW

    fun textField(
            image: Image,
            hint: String,
            help: String,
            type: TextInputType,
            error: ObservableProperty<String>,
            text: MutableObservableProperty<String>
    ): VIEW

    fun textArea(
            image: Image,
            hint: String,
            help: String,
            type: TextInputType,
            error: ObservableProperty<String>,
            text: MutableObservableProperty<String>
    ): VIEW

    fun numberField(
            image: Image,
            hint: String,
            help: String,
            type: NumberInputType,
            error: ObservableProperty<String>,
            decimalPlaces: Int = 0,
            value: MutableObservableProperty<Number?>
    ): VIEW

    fun datePicker(
            observable: MutableObservableProperty<Date>
    ): VIEW

    fun dateTimePicker(
            observable: MutableObservableProperty<DateTime>
    ): VIEW

    fun timePicker(
            observable: MutableObservableProperty<Time>
    ): VIEW

    fun slider(
            range: IntRange,
            observable: MutableObservableProperty<Int>
    ): VIEW

    fun toggle(
            observable: MutableObservableProperty<Boolean>
    ): VIEW

    fun toggleButton(
            image: ObservableProperty<Image?> = ConstantObservableProperty(null),
            label: ObservableProperty<String?> = ConstantObservableProperty(null),
            value: MutableObservableProperty<Boolean>
    ): VIEW


    //Containers

    fun refresh(
            contains: VIEW,
            working:ObservableProperty<Boolean>,
            onRefresh: () -> Unit
    ): VIEW

    fun scroll(
            view: VIEW
    ): VIEW

    fun margin(
            left: Float = 0f,
            top: Float = 0f,
            right: Float = 0f,
            bottom: Float = 0f,
            view: VIEW
    ): VIEW

    fun swap(
            view: ObservableProperty<Pair<VIEW, Animation>>
    ): VIEW

    fun space(size: Point): VIEW

    fun horizontal(
            vararg views: Pair<PlacementPair, VIEW>
    ): VIEW

    fun vertical(
            vararg views: Pair<PlacementPair, VIEW>
    ): VIEW

    fun frame(
            vararg views: Pair<PlacementPair, VIEW>
    ): VIEW


    //Modifiers
    fun background(
            view: VIEW,
            color: ObservableProperty<Color>
    ): VIEW

    fun card(
            view: VIEW
    ): VIEW

    fun alpha(
            view: VIEW,
            alpha: ObservableProperty<Float>
    ): VIEW

    fun clickable(
            view: VIEW,
            onClick: () -> Unit
    ): VIEW
}