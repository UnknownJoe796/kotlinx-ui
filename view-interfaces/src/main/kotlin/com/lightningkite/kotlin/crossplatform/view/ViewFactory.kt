package com.lightningkite.kotlin.crossplatform.view

import com.lightningkite.kotlin.crossplatform.view.old.FieldView
import javafx.collections.ObservableList
import lk.kotlin.observable.property.MutableObservableProperty
import lk.kotlin.observable.property.ObservableProperty
import lk.kotlin.observable.property.StackObservableProperty
import java.util.*

interface ViewFactory<View> {

    //Navigation

    fun window(
            stack: StackObservableProperty<() -> View>,
            tabs: List<Pair<TabItem, () -> View>>,
            actions: ObservableList<TabItem>
    ): View

    fun pages(
            vararg pageGenerator: () -> View
    ): View

    fun tabs(
            options: ObservableList<TabItem>,
            selected: MutableObservableProperty<TabItem>
    ): View


    //Collection Views

    fun <T> list(
            data: ObservableList<T>,
            itemToString: (T) -> String = { it.toString() },
            onBottom: () -> Unit = {},
            makeView: (type: Int, obs: ObservableProperty<T>) -> View
    ): View

    fun <T> grid(
            minItemSize: Float = Float.MAX_VALUE,
            data: ObservableList<T>,
            itemToString: (T) -> String = { it.toString() },
            onBottom: () -> Unit = {},
            makeView: (type: Int, obs: ObservableProperty<T>) -> View
    ): View


    //Pre-made items

    fun listHeader(
            title: ObservableProperty<String>,
            subtitle: ObservableProperty<String?>,
            icon: ObservableProperty<Image?>,
            onClick: ObservableProperty<() -> Unit>
    ): View

    fun listItem(
            title: ObservableProperty<String>,
            subtitle: ObservableProperty<String?>,
            icon: ObservableProperty<Image?>
    ): View

    fun listItem(
            title: ObservableProperty<String>,
            subtitle: ObservableProperty<String?>,
            icon: ObservableProperty<Image?>,
            onClick: ObservableProperty<() -> Unit>
    ): View

    fun listItem(
            title: ObservableProperty<String>,
            subtitle: ObservableProperty<String?>,
            icon: ObservableProperty<Image?>,
            toggle: MutableObservableProperty<Boolean>
    ): View


    //Display

    fun header(
            text: ObservableProperty<String>
    ): View

    fun subheader(
            text: ObservableProperty<String>
    ): View

    fun body(
            text: ObservableProperty<String>
    ): View

    fun work(): View

    fun progress(
            observable: ObservableProperty<Float>
    ): View

    fun image(
            minSize: Point,
            image: ObservableProperty<Image>
    ): View


    //Controls

    fun button(
            image: ObservableProperty<Image?>,
            label: ObservableProperty<String?>
    ): View

    fun <T> picker(
            options: ObservableList<T>,
            selected: MutableObservableProperty<T>,
            makeView: (obs: ObservableProperty<T>) -> View
    ): View

    fun field(
            image: Image,
            hint: String,
            help: String,
            type: FieldView.Type,
            error: ObservableProperty<String>,
            text: MutableObservableProperty<String>
    ): View

    fun datePicker(
            observable: MutableObservableProperty<Date>
    ): View

    fun dateTimePicker(
            observable: MutableObservableProperty<Date>
    ): View

    fun timePicker(
            observable: MutableObservableProperty<Date>
    ): View

    fun slider(
            steps: Int = Int.MAX_VALUE,
            observable: MutableObservableProperty<Float>
    ): View

    fun toggle(
            observable: MutableObservableProperty<Boolean>
    ): View


    //Containers

    fun refresh(
            contains: View,
            onRefresh: () -> Unit
    ): View

    fun scroll(
            view: View
    ): View

    fun swap(
            view: ObservableProperty<Pair<View, Animation>>
    ): View

    fun horizontal(
            spacing: Float = 0f,
            vararg views: Pair<Gravity, View>
    ): View

    fun vertical(
            spacing: Float = 0f,
            vararg views: Pair<Gravity, View>
    ): View

    fun frame(
            vararg views: Pair<Gravity, View>
    ): View

    fun codeLayout(
            vararg views: Pair<(View) -> Rectangle, View>
    ): View
}