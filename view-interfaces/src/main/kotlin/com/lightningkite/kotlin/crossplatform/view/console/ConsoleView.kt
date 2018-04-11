package com.lightningkite.kotlin.crossplatform.view.console

import com.lightningkite.kotlin.crossplatform.view.old.View
import java.io.Closeable

interface ConsoleView : View, Closeable {
    fun write(screen: ConsoleScreen, appendable: TabAppendable)
    fun parseInput(line: String)
}

