package com.lightningkite.kotlinx.ui.views.virtual

import com.lightningkite.kotlinx.collection.recursiveFlatMap
import com.lightningkite.kotlinx.ui.geometry.Rectangle

abstract class View {
    var location: Rectangle = Rectangle()
    var isAttached: Boolean = false
}

abstract class ContainerView : View() {
    abstract fun listViews(): List<View>

    fun recursiveViews(): Sequence<View> = listViews().asSequence().recursiveFlatMap {
        (it as? ContainerView)?.listViews()?.asSequence() ?: sequenceOf()
    }
}