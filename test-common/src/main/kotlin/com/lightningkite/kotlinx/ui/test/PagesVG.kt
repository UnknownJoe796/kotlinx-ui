package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.*

class PagesVG<VIEW>(val factory: ViewFactory<VIEW>) : ViewGenerator<VIEW> {
    override val title: String = "Pages"

    override fun generate(): VIEW = with(factory) {
        pagesEmbedded(
                StandardObservableProperty(0),
                {
                    text(size = TextSize.Header, text = "First page")
                },
                {
                    text(size = TextSize.Header, text = "Second page")
                },
                {
                    text(size = TextSize.Header, text = "Third page")
                },
                {
                    text(size = TextSize.Header, text = "Last page")
                }
        )
    }
}
