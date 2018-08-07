package com.lightningkite.kotlinx.ui.android

import android.view.View

var View.lifecycle
    get() = com.lightningkite.kotlinx.ui.implementationhelpers.AnyLifecycles.getOrPut(this) { com.lightningkite.kotlinx.ui.implementationhelpers.TreeObservableProperty() }
    set(value) {
        com.lightningkite.kotlinx.ui.implementationhelpers.AnyLifecycles[this] = value
    }