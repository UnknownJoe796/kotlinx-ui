package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.Point
import com.lightningkite.kotlinx.ui.ViewFactory
import com.lightningkite.kotlinx.ui.ViewGenerator

class SpaceTestVG<VIEW>(val factory: ViewFactory<VIEW>) : ViewGenerator<VIEW> {
    override val title: String = "Space"
    override fun generate(): VIEW = with(factory) {
        space(Point(32f, 32f))
    }
}
