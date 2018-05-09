package com.lightningkite.kotlinx.ui.virtual

import com.lightningkite.kotlinx.ui.Rectangle


abstract class View {
    var location: Rectangle = Rectangle()
    var isAttached: Boolean = false
}

abstract class ContainerView : View() {
    abstract fun views(): List<View>

    fun recursiveViews(): Sequence<View> = views().asSequence().recursiveFlatMap {
        (it as? ContainerView)?.views()?.asSequence() ?: sequenceOf()
    }
}