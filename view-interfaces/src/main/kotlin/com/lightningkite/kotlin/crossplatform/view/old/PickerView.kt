package com.lightningkite.kotlin.crossplatform.view.old

import lk.kotlin.observable.list.ObservableList
import lk.kotlin.observable.property.MutableObservableProperty

interface PickerView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    val selectedIndex: MutableObservableProperty<Int>
    var items: ObservableList<String>
}