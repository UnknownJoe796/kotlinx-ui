package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.lambda.invokeAll
import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.ui.geometry.Point
import kotlin.browser.window

actual object ApplicationAccess {
    actual val displaySize: ObservableProperty<Point>
        get() = ConstantObservableProperty(Point(window.innerWidth.toFloat(), window.innerHeight.toFloat()))
    actual val isInForeground: ObservableProperty<Boolean> = ConstantObservableProperty(true)

    actual val onBackPressed: MutableList<() -> Boolean> = ArrayList()
    actual val onAnimationFrame: MutableCollection<() -> Unit> by lazy {
        val list = ArrayList<() -> Unit>()
        window.setInterval({ list.invokeAll() }, 30)
        list
    }

    actual fun runLater(action: () -> Unit) {
        window.setTimeout(action, 1)
    }

    actual fun runAfterDelay(delayMilliseconds: Long, action: () -> Unit) {
        window.setTimeout(action, delayMilliseconds.toInt())
    }

}