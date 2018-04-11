package com.lightningkite.kotlin.crossplatform.view.console

import com.lightningkite.kotlin.crossplatform.view.old.TextView

class TextConsole(val requestRender: CanRequestRender) : TextView, ConsoleViewImpl() {
    override var style: TextView.Style = TextView.DefaultStyle
        set(value) {
            field = value
            requestRender.requestRender()
        }
    override var text: String = ""
        set(value) {
            field = value
            requestRender.requestRender()
        }

    override fun write(screen: ConsoleScreen, appendable: TabAppendable) {
        appendable.append(text)
    }

    override fun parseInput(line: String) {}
}