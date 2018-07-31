package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.observable.property.ObservablePropertyMapped
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.observable.property.transform

fun <T> StackObservableProperty<T>.withAnimations(
        push: Animation = Animation.Push,
        pop: Animation = Animation.Pop,
        other: Animation = Animation.Fade
): ObservablePropertyMapped<T, Pair<T, Animation>> {
    var current = this.stack.size
    return this.transform {
        val newSize = this.stack.size
        val result = it to when {
            newSize < current -> pop
            newSize == current -> other
            newSize > current -> push
            else -> other
        }
        current = newSize
        result
    }
}