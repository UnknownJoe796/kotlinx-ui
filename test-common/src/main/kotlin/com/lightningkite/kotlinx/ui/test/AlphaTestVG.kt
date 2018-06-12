package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.*

class AlphaTestVG<VIEW>(val factory: ViewFactory<VIEW>) : ViewGenerator<VIEW> {
    override val title: String = "Alpha"
    val alpha = StandardObservableProperty(0f)

    override fun generate(): VIEW = with(factory) {


        vertical(
                PlacementPair.topFill to button(label = ConstantObservableProperty("Change Alpha"), onClick = {
                    alpha.value = if (alpha.value < .5f) 1f else 0f
                }),
                PlacementPair.topFill to text(text = "Header", size = TextSize.Header).alpha(alpha)
        )
    }
}
