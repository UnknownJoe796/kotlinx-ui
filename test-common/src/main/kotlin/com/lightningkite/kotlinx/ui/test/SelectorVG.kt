package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.observable.property.transform
import com.lightningkite.kotlinx.ui.Importance
import com.lightningkite.kotlinx.ui.ViewFactory
import com.lightningkite.kotlinx.ui.ViewGenerator

class SelectorVG<VIEW>(
        val stack: StackObservableProperty<ViewGenerator<ViewFactory<VIEW>, VIEW>>
) : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "KotlinX UI Test"

    val tests = observableListOf(
            "Space Test" to { SpaceTestVG<VIEW>() },
            "Original Test" to { OriginalTestVG<VIEW>() },
            "Alpha" to { AlphaTestVG<VIEW>() },
            "Horizontal" to { HorizontalVG<VIEW>() },
            "Pages" to { PagesVG<VIEW>() },
            "Frame" to { FrameVG<VIEW>() },
            "Web Load Test" to { WebLoadTestVG<VIEW>() },
            "Controls" to { ControlsVG<VIEW>() }
    )

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        list(data = tests, onBottom = {}, makeView = { itemObs ->
            button(
                    label = itemObs.transform { item -> item.first },
                    importance = Importance.Low,
                    onClick = { stack.push(itemObs.value.second.invoke()) }
            )
        }).margin(8f)
    }
}
