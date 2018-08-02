package com.lightningkite.kotlinx.ui.geometry

class LinearPlacement(
        val weight: Float = 0f,
        val align: Align = Align.Fill
) {
    companion object {
        val fillStart = LinearPlacement(0f, Align.Start)
        val fillCenter = LinearPlacement(0f, Align.Center)
        val fillEnd = LinearPlacement(0f, Align.End)
        val fillFill = LinearPlacement(0f, Align.Fill)
        val wrapStart = LinearPlacement(1f, Align.Start)
        val wrapCenter = LinearPlacement(1f, Align.Center)
        val wrapEnd = LinearPlacement(1f, Align.End)
        val wrapFill = LinearPlacement(1f, Align.Fill)
    }
}