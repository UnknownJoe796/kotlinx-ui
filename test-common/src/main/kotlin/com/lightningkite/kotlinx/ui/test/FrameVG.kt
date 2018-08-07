package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.builders.text
import com.lightningkite.kotlinx.ui.concepts.TextSize
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

class FrameVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Horizontal"

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        frame(
                AlignPair.TopLeft to text(text = "Top Left", size = TextSize.Body),
                AlignPair.CenterLeft to text(text = "Center Left", size = TextSize.Body),
                AlignPair.BottomLeft to text(text = "Bottom Left", size = TextSize.Body),
                AlignPair.TopCenter to text(text = "Top Center", size = TextSize.Body),
                AlignPair.CenterCenter to text(text = "Center Center", size = TextSize.Body),
                AlignPair.FillFill to text(text = "Fill", size = TextSize.Header, alignPair = AlignPair.TopLeft),
                AlignPair.BottomCenter to text(text = "Bottom Center", size = TextSize.Body),
                AlignPair.TopRight to text(text = "Top Right", size = TextSize.Body),
                AlignPair.CenterRight to text(text = "Center Right", size = TextSize.Body),
                AlignPair.BottomRight to text(text = "Bottom Right", size = TextSize.Body)
        )
    }
}
