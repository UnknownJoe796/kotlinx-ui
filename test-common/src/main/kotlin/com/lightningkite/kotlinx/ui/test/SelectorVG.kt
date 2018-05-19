package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.observable.property.transform
import com.lightningkite.kotlinx.ui.ViewFactory
import com.lightningkite.kotlinx.ui.ViewGenerator

class SelectorVG<VIEW>(
        val factory: ViewFactory<VIEW>,
        val stack: StackObservableProperty<ViewGenerator<VIEW>>
) : ViewGenerator<VIEW> {
    override val title: String = "KotlinX UI Test"

    val tests = observableListOf(
            "Original Test" to { OriginalTestVG(factory) },
            "Alpha" to { AlphaTestVG(factory) },
            "Horizontal" to { HorizontalVG(factory) },
            "Pages" to { PagesVG(factory) },
            "Frame" to { FrameVG(factory) },
            "Web Load Test" to { WebLoadTestVG(factory) }
    )

    override fun generate(): VIEW = with(factory) {
        list(data = tests, onBottom = {}, makeView = { itemObs ->
            button(
                    label = itemObs.transform { item -> item.first },
                    onClick = { stack.push(itemObs.value.second.invoke()) }
            )
        })
    }
}
