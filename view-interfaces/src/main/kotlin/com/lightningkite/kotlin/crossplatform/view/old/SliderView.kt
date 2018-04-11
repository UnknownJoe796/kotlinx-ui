package com.lightningkite.kotlin.crossplatform.view.old

import lk.kotlin.observable.property.MutableObservableProperty

interface SliderView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    var steps: Int
    val date: MutableObservableProperty<Float>
}