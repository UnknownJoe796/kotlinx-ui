package com.lightningkite.kotlinx.ui

class PlacementList<VIEW>() : MutableList<Pair<PlacementPair, VIEW>> by ArrayList<Pair<PlacementPair, VIEW>>() {

    var defaultPlacement = PlacementPair.centerCenter

    operator fun VIEW.unaryPlus() {
        add(defaultPlacement to this)
    }

    operator fun PlacementPair.plus(view: VIEW) {
        add(this to view)
    }

    operator fun plusAssign(view: VIEW) {
        add(defaultPlacement to view)
    }
}

fun <VIEW> ViewFactory<VIEW>.horizontal(setup: PlacementList<VIEW>.() -> Unit): VIEW {
    val list = PlacementList<VIEW>()
    list.setup()
    return horizontal(*list.toTypedArray())
}

fun <VIEW> ViewFactory<VIEW>.vertical(setup: PlacementList<VIEW>.() -> Unit): VIEW {
    val list = PlacementList<VIEW>()
    list.setup()
    return vertical(*list.toTypedArray())
}

fun <VIEW> ViewFactory<VIEW>.frame(setup: PlacementList<VIEW>.() -> Unit): VIEW {
    val list = PlacementList<VIEW>()
    list.setup()
    return frame(*list.toTypedArray())
}