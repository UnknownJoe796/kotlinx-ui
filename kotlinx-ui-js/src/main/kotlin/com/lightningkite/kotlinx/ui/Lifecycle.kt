package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.ui.helper.AnyLifecycles
import com.lightningkite.kotlinx.ui.helper.TreeObservableProperty
import org.w3c.dom.HTMLElement


var HTMLElement.lifecycle
    get() = AnyLifecycles.getOrPut(this) { TreeObservableProperty() }
    set(value) {
        AnyLifecycles[this] = value
    }