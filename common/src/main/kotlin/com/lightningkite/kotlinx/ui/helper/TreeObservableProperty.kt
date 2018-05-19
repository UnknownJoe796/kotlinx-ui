package com.lightningkite.kotlinx.ui.helper

import com.lightningkite.kotlinx.collection.WeakHashMap
import com.lightningkite.kotlinx.lambda.invokeAll
import com.lightningkite.kotlinx.observable.property.ObservableProperty

class TreeObservableProperty(parent: TreeObservableProperty? = null) : ObservableProperty<Boolean> {
    var parent: TreeObservableProperty? = null
        set(value) {
            field = value
            broadcast()
        }
    var myValue: Boolean = true
        private set
    override val value: Boolean get() = myValue && (parent?.value ?: true)
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
            previousValue = value
            listeners.invokeAll(newValue)
            for (child in children) {
                child.broadcast()
            }
        }
    }

    fun on() {
        myValue = true
        broadcast()
    }

    fun off() {
        myValue = false
        broadcast()
    }

    fun child() = TreeObservableProperty(this)
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