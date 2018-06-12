package com.lightningkite.kotlinx.ui.android

import android.view.View

var View.lifecycle
    get() = com.lightningkite.kotlinx.ui.helper.AnyLifecycles.getOrPut(this) { com.lightningkite.kotlinx.ui.helper.TreeObservableProperty() }
    set(value) {
        com.lightningkite.kotlinx.ui.helper.AnyLifecycles[this] = value
    }