package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.ui.ViewFactory
import com.lightningkite.kotlinx.ui.ViewGenerator

class MainVG<VIEW>(val factory: ViewFactory<VIEW>) : ViewGenerator<VIEW> {
    override val title: String = "KotlinX UI Test"

    val stack = StackObservableProperty<ViewGenerator<VIEW>>()

    init {
        stack.push(SelectorVG(factory, stack))
    }

    override fun generate(): VIEW = with(factory) {
        window(
                stack = stack,
                tabs = listOf(),
                actions = observableListOf()
        )
    }
}