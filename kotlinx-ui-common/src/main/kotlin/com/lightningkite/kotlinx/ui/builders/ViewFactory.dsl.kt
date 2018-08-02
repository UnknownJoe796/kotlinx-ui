package com.lightningkite.kotlinx.ui.builders

import com.lightningkite.kotlinx.ui.geometry.Align
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.geometry.LinearPlacement
import com.lightningkite.kotlinx.ui.views.ViewFactory

class LinearBuilder<VIEW>() : MutableList<Pair<LinearPlacement, VIEW>> by ArrayList<Pair<LinearPlacement, VIEW>>() {

    var defaultAlign = Align.Fill

    operator fun VIEW.unaryMinus() {
        add(LinearPlacement(0f, defaultAlign) to this)
    }

    operator fun VIEW.unaryPlus() {
        add(LinearPlacement(1f, defaultAlign) to this)
    }
}

fun <VIEW> ViewFactory<VIEW>.horizontal(setup: LinearBuilder<VIEW>.() -> Unit): VIEW {
    val list = LinearBuilder<VIEW>()
    list.setup()
    return horizontal(*list.toTypedArray())
}

fun <VIEW> ViewFactory<VIEW>.vertical(setup: LinearBuilder<VIEW>.() -> Unit): VIEW {
    val list = LinearBuilder<VIEW>()
    list.setup()
    return vertical(*list.toTypedArray())
}

class AlignPairBuilder<VIEW>() : MutableList<Pair<AlignPair, VIEW>> by ArrayList<Pair<AlignPair, VIEW>>() {

    var defaultAlign = Align.Fill

    operator fun AlignPair.plus(view: VIEW) {
        add(this to view)
    }

    operator fun AlignPair.minus(view: VIEW) {
        add(this to view)
    }
}

fun <VIEW> ViewFactory<VIEW>.frame(setup: AlignPairBuilder<VIEW>.() -> Unit): VIEW {
    val list = AlignPairBuilder<VIEW>()
    list.setup()
    return frame(*list.toTypedArray())
}