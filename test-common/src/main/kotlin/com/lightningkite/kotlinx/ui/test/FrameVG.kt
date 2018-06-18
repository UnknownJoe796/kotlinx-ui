package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.*

class FrameVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Horizontal"

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        frame(
                PlacementPair(Placement.wrapStart, Placement.wrapStart) to text(text = "Top Left", size = TextSize.Body),
                PlacementPair(Placement.wrapStart, Placement.wrapCenter) to text(text = "Center Left", size = TextSize.Body),
                PlacementPair(Placement.wrapStart, Placement.wrapEnd) to text(text = "Bottom Left", size = TextSize.Body),
                PlacementPair(Placement.wrapCenter, Placement.wrapStart) to text(text = "Top Center", size = TextSize.Body),
                PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to text(text = "Center Center", size = TextSize.Body),
                PlacementPair(Placement.fill, Placement.fill) to text(text = "Fill", size = TextSize.Header, alignPair = AlignPair.TopLeft),
                PlacementPair(Placement.wrapCenter, Placement.wrapEnd) to text(text = "Bottom Center", size = TextSize.Body),
                PlacementPair(Placement.wrapEnd, Placement.wrapStart) to text(text = "Top Right", size = TextSize.Body),
                PlacementPair(Placement.wrapEnd, Placement.wrapCenter) to text(text = "Center Right", size = TextSize.Body),
                PlacementPair(Placement.wrapEnd, Placement.wrapEnd) to text(text = "Bottom Right", size = TextSize.Body)
        )
    }
}
