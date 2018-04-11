package com.lightningkite.kotlin.crossplatform.view.old

import lk.kotlin.observable.property.ObservableProperty

interface SwapView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    enum class Animation {
        Push, Pop, GoDown, GoUp, Fade, Flip
    }

    val view: ObservableProperty<View>
    fun swap(newView: View, animation: Animation)
}