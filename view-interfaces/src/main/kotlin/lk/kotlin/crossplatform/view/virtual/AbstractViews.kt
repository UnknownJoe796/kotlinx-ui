package lk.kotlin.crossplatform.view.virtual

import lk.kotlin.crossplatform.view.Rectangle
import lk.kotlin.utils.collection.recursiveFlatMap


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