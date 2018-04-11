package com.lightningkite.kotlin.crossplatform.view.console

import com.lightningkite.kotlin.crossplatform.view.old.FieldView
import lk.kotlin.observable.property.MutableObservableProperty
import lk.kotlin.observable.property.StandardObservableProperty
import lk.kotlin.observable.property.plusAssign

class FieldConsole(val requestRender: CanRequestRender) : FieldView, ConsoleViewImpl() {
    override var hint: String = ""
        set(value) {
            field = value
            requestRender.requestRender()
        }
    override var style: FieldView.Style = FieldView.DefaultStyle
        set(value) {
            field = value
            requestRender.requestRender()
        }
    override var type: FieldView.Type = FieldView.Type.Name
    override val text: MutableObservableProperty<String> = StandardObservableProperty("").apply {
        this += {
            requestRender.requestRender()
        }
    }

    override fun write(screen: ConsoleScreen, appendable: TabAppendable) {
        val id = screen.requestIdentifier(this, hint.toLowerCase())
        appendable.append(hint.replace(id, "[${id.toUpperCase()}]", true) + ": ${text.value}")
    }

    override fun parseInput(line: String) {
        text.value = line
    }
}