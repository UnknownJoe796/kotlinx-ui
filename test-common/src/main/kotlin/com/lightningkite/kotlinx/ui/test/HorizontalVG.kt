package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.*

class HorizontalVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Horizontal"

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        horizontal(
                PlacementPair.fillLeft to text(text = "left", alignPair = AlignPair.CenterCenter),
                PlacementPair.fillFill to text(text = "fill", alignPair = AlignPair.CenterCenter),
                PlacementPair.fillRight to text(text = "right", alignPair = AlignPair.CenterCenter)
        )
    }
}
