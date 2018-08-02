package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.reflection.KxReflection
import com.lightningkite.kotlinx.reflection.setupGenerated
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

class MainVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "KotlinX UI Test"

    val stack = StackObservableProperty<ViewGenerator<ViewFactory<VIEW>, VIEW>>()

    init {
        //Startup
        KxReflection
        setupGenerated()

        stack.push(SelectorVG(stack))
    }

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        window(
                dependency = dependency,
                stack = stack,
                tabs = listOf(),
                actions = observableListOf()
        )
    }
}