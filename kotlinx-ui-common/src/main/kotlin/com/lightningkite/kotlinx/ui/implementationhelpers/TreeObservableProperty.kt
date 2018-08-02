package com.lightningkite.kotlinx.ui.implementationhelpers

import com.lightningkite.kotlinx.collection.WeakHashMap
import com.lightningkite.kotlinx.lambda.invokeAll
import com.lightningkite.kotlinx.observable.property.ObservableProperty

class TreeObservableProperty() : ObservableProperty<Boolean> {
    var parent: TreeObservableProperty? = null
        set(value) {
            if (value == this) throw IllegalArgumentException()
            field?.children?.remove(this)
            field = value
            value?.children?.add(this)
            broadcast()
        }
    var alwaysOn: Boolean = false
        set(value) {
            field = value
            broadcast()
        }
    override val value: Boolean get() = (parent?.value ?: alwaysOn)
    var previousValue: Boolean = value

    val children = ArrayList<TreeObservableProperty>()
    fun add(element: TreeObservableProperty): Boolean = children.add(element)
    fun remove(element: TreeObservableProperty): Boolean = children.remove(element)

    val listeners = ArrayList<(Boolean) -> Unit>()
    override fun add(element: (Boolean) -> Unit): Boolean = listeners.add(element)
    override fun remove(element: (Boolean) -> Unit): Boolean = listeners.remove(element)

    fun broadcast() {
        val newValue = value
        if (newValue != previousValue) {
            previousValue = newValue
            listeners.invokeAll(newValue)
            for (child in children) {
                child.broadcast()
            }
        }
    }
}

val AnyLifecycles = WeakHashMap<Any, TreeObservableProperty>()
//var Any.lifecycle
//        get() = AnyLifecycles.getOrPut(this){ TreeObservableProperty() }
//        set(value){
//            AnyLifecycles[this] = value
//        }
//fun Any.lifecycleChildOf(parent:Any){
//    this.lifecycle = parent.lifecycle.child()
//}