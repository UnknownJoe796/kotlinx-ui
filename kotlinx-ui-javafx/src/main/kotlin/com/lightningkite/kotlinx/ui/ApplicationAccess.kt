package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.ui.geometry.Point
import javafx.application.Platform

actual object ApplicationAccess {
    actual val displaySize: ObservableProperty<Point> //can change on rotation, etc.
        get() = TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    actual val isInForeground: ObservableProperty<Boolean>
        get() = TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    actual val onBackPressed: MutableList<() -> Boolean>
        get() = TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    actual val onAnimationFrame: MutableCollection<() -> Unit>
        get() = TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    actual fun runLater(action: () -> Unit) {
        Platform.runLater(action)
    }

    actual fun runAfterDelay(delayMilliseconds: Long, action: () -> Unit) {

    }

}