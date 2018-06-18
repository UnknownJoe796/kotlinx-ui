package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.*

class AlphaTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Alpha"
    val alpha = StandardObservableProperty(0f)

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {


        vertical(
                PlacementPair.topFill to button(label = ConstantObservableProperty("Change Alpha"), onClick = {
                    alpha.value = if (alpha.value < .5f) 1f else 0f
                }),
                PlacementPair.topFill to text(text = "Header", size = TextSize.Header).alpha(alpha)
        )
    }
}
