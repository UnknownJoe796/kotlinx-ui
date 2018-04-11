package com.lightningkite.kotlin.crossplatform.view.old

import lk.kotlin.observable.property.MutableObservableProperty

interface ToggleView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    val state: MutableObservableProperty<Boolean>
}