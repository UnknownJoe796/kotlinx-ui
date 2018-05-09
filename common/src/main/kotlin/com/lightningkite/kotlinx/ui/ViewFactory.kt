package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.locale.*
import com.lightningkite.kotlinx.observable.property.*
import com.lightningkite.kotlinx.observable.list.*

interface ViewFactory<VIEW> {

    //Navigation

    fun window(
            stack: StackObservableProperty<() -> VIEW>,
            tabs: List<Pair<TabItem, () -> VIEW>>,
            actions: ObservableList<Pair<TabItem, ()->Unit>>
    ): VIEW

    fun pages(
            vararg pageGenerator: () -> VIEW
    ): VIEW

    fun tabs(
            options: ObservableList<TabItem>,
            selected: MutableObservableProperty<TabItem>
    ): VIEW


    //Collection Views

    fun <T> list(
            data: ObservableList<T>,
            onBottom: () -> Unit,
            itemToType: (T) -> Int,
            makeView: (type: Int, obs: ObservableProperty<T>) -> VIEW
    ): VIEW

    fun <T> grid(
            minItemSize: Float = Float.MAX_VALUE,
            data: ObservableList<T>,
            onBottom: () -> Unit = {},
            itemToType: (T) -> Int,
            makeView: (type: Int, obs: ObservableProperty<T>) -> VIEW
    ): VIEW
    

    //Display

    fun header(
            text: ObservableProperty<String>
    ): VIEW

    fun subheader(
            text: ObservableProperty<String>
    ): VIEW

    fun body(
            text: ObservableProperty<String>
    ): VIEW

    fun work(): VIEW

    fun progress(
            observable: ObservableProperty<Float>
    ): VIEW

    fun image(
            minSize: Point,
            image: ObservableProperty<Image>
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

    fun field(
            image: Image,
            hint: String,
            help: String,
            type: InputType,
            error: ObservableProperty<String>,
            text: MutableObservableProperty<String>
    ): VIEW

    fun datePicker(
            observable: MutableObservableProperty<Date>
    ): VIEW

    fun dateTimePicker(
            observable: MutableObservableProperty<Date>
    ): VIEW

    fun timePicker(
            observable: MutableObservableProperty<Date>
    ): VIEW

    fun slider(
            steps: Int = Int.MAX_VALUE,
            observable: MutableObservableProperty<Float>
    ): VIEW

    fun toggle(
            observable: MutableObservableProperty<Boolean>
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

    fun horizontal(
            spacing: Float = 0f,
            vararg views: Pair<Gravity, VIEW>
    ): VIEW

    fun vertical(
            spacing: Float = 0f,
            vararg views: Pair<Gravity, VIEW>
    ): VIEW

    fun frame(
            vararg views: Pair<Gravity, VIEW>
    ): VIEW

    fun codeLayout(
            vararg views: Pair<(MutableList<Rectangle>) -> Rectangle, VIEW>
    ): VIEW
}