package com.lightningkite.kotlin.crossplatform.view.console

import com.lightningkite.kotlin.crossplatform.view.old.ContainerView
import com.lightningkite.kotlin.crossplatform.view.old.View
import lk.kotlin.observable.list.ObservableListWrapper
import lk.kotlin.observable.property.plusAssign

class ContainerConsole(val requestRender: CanRequestRender) : ContainerView, ConsoleViewImpl() {
    override val views: MutableList<View> = ObservableListWrapper<View>(ArrayList()).apply {
        this.onUpdate += {
            requestRender.requestRender()
        }
    }
    override var style: ContainerView.Style = ContainerView.DefaultStyle
        set(value) {
            field = value
            requestRender.requestRender()
        }

    override fun write(screen: ConsoleScreen, appendable: TabAppendable) {
        appendable.tabLevel++
        for (item in views) {
            (item as ConsoleView).write(screen, appendable)
            appendable.appendln()
        }
        appendable.tabLevel--
    }

    override fun parseInput(line: String) {}
}