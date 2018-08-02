package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.ui.geometry.Point
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

class SpaceTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Space"
    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        space(Point(32f, 32f))
    }
}
