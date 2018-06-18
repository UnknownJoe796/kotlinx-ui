package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.Point
import com.lightningkite.kotlinx.ui.ViewFactory
import com.lightningkite.kotlinx.ui.ViewGenerator

class SpaceTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Space"
    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        space(Point(32f, 32f))
    }
}
