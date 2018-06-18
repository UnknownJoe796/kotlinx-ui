package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.*

class VerticalTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Horizontal"

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        vertical(
                PlacementPair.topLeft to text(text = "left", alignPair = AlignPair.CenterCenter),
                PlacementPair.topFill to text(text = "fill", alignPair = AlignPair.CenterCenter),
                PlacementPair.topRight to text(text = "right", alignPair = AlignPair.CenterCenter),
                PlacementPair.topLeft to text(text = "left", alignPair = AlignPair.CenterCenter),
                PlacementPair.topFill to text(text = "fill", alignPair = AlignPair.CenterCenter),
                PlacementPair.topRight to text(text = "right", alignPair = AlignPair.CenterCenter),
                PlacementPair.topLeft to text(text = "left", alignPair = AlignPair.CenterCenter),
                PlacementPair.topFill to text(text = "fill", alignPair = AlignPair.CenterCenter),
                PlacementPair.topRight to text(text = "right", alignPair = AlignPair.CenterCenter)
        )
    }
}
