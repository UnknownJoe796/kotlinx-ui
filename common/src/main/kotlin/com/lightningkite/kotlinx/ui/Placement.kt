package com.lightningkite.kotlinx.ui

data class Placement(
        val align: Align,
        val size: Float
) {
    constructor(align: Align) : this(
            align,
            if (align == Align.Fill) 1f else 0f
    )

    companion object {
        val wrapCenter = Placement(Align.Center, 0f)
        val wrapStart = Placement(Align.Start, 0f)
        val wrapEnd = Placement(Align.End, 0f)
        val fill = Placement(Align.Fill, 1f)
    }
}