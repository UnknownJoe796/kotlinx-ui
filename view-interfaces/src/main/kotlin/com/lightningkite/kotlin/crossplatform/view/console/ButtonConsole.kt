package com.lightningkite.kotlin.crossplatform.view.console

import com.lightningkite.kotlin.crossplatform.view.old.ButtonView
import lk.kotlin.utils.lambda.invokeAll

class ButtonConsole(val requestRender: CanRequestRender) : ButtonView, ConsoleViewImpl() {
    override var style: ButtonView.Style = ButtonView.DefaultStyle
        set(value) {
            field = value
            requestRender.requestRender()
        }
    override var text: String = ""
        set(value) {
            field = value
            requestRender.requestRender()
        }
    override val onClick: MutableCollection<() -> Unit> = ArrayList()

    override fun write(screen: ConsoleScreen, appendable: TabAppendable) {
        val id = screen.requestIdentifier(this, text.toLowerCase())
        appendable.appendln(text.replace(id, "[${id.toUpperCase()}]", true))
    }

    override fun parseInput(line: String) {
        onClick.invokeAll()
    }
}