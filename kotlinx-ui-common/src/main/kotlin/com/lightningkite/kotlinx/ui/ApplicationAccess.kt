package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.ui.geometry.Point

expect object ApplicationAccess {
    val displaySize: ObservableProperty<Point> //can change on rotation, etc.
    val isInForeground: ObservableProperty<Boolean>
    val onBackPressed: MutableList<() -> Boolean>
    val onAnimationFrame: MutableCollection<() -> Unit>

    fun runLater(action: () -> Unit)
    fun runAfterDelay(delayMilliseconds: Long, action: () -> Unit)
}