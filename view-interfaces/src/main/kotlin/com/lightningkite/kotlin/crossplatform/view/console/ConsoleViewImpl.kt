package com.lightningkite.kotlin.crossplatform.view.console

import lk.kotlin.lifecycle.CloseableLifecycle

abstract class ConsoleViewImpl() : ConsoleView {

    override val lifecycle: CloseableLifecycle = CloseableLifecycle()
    override fun close() = lifecycle.close()
}