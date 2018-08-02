package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.builders.pagesEmbedded
import com.lightningkite.kotlinx.ui.builders.text
import com.lightningkite.kotlinx.ui.concepts.TextSize
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

class PagesVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Pages"

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        pagesEmbedded(
                dependency,
                StandardObservableProperty(0),
                {
                    text(size = TextSize.Header, text = "First page", alignPair = AlignPair.CenterCenter)
                },
                {
                    text(size = TextSize.Header, text = "Second page", alignPair = AlignPair.CenterCenter)
                },
                {
                    text(size = TextSize.Header, text = "Third page", alignPair = AlignPair.CenterCenter)
                },
                {
                    text(size = TextSize.Header, text = "Last page", alignPair = AlignPair.CenterCenter)
                }
        )
    }
}
