package com.lightningkite.kotlin.crossplatform.view.old

import lk.kotlin.lifecycle.LifecycleConnectable

interface View {
    open class Style()

    val style: Style
    val lifecycle: LifecycleConnectable
}