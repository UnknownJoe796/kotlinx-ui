package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.builders.text
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.geometry.LinearPlacement
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

class HorizontalVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Horizontal"

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        horizontal(
                LinearPlacement.fillStart to text(text = "left", alignPair = AlignPair.CenterCenter),
                LinearPlacement.fillFill to text(text = "fill", alignPair = AlignPair.CenterCenter),
                LinearPlacement.fillEnd to text(text = "right", alignPair = AlignPair.CenterCenter)
        )
    }
}
