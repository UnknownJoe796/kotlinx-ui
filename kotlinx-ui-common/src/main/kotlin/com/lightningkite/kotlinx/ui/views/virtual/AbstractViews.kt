package com.lightningkite.kotlinx.ui.views.virtual

import com.lightningkite.kotlinx.collection.recursiveFlatMap
import com.lightningkite.kotlinx.ui.geometry.Rectangle

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