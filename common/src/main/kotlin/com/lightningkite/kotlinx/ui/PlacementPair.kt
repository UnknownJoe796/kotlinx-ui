package com.lightningkite.kotlinx.ui

data class PlacementPair(
        val horizontal: Placement,
        val vertical: Placement
) {
    constructor(pair: AlignPair) : this(Placement(pair.horizontal), Placement(pair.vertical))

    val alignPair get() = alignPair(horizontal.align, vertical.align)

    companion object {
        val topLeft = PlacementPair(AlignPair.TopLeft)
        val topCenter = PlacementPair(AlignPair.TopCenter)
        val topFill = PlacementPair(AlignPair.TopFill)
        val topRight = PlacementPair(AlignPair.TopRight)
        val centerLeft = PlacementPair(AlignPair.CenterLeft)
        val centerCenter = PlacementPair(AlignPair.CenterCenter)
        val centerFill = PlacementPair(AlignPair.CenterFill)
        val centerRight = PlacementPair(AlignPair.CenterRight)
        val fillLeft = PlacementPair(AlignPair.FillLeft)
        val fillCenter = PlacementPair(AlignPair.FillCenter)
        val fillFill = PlacementPair(AlignPair.FillFill)
        val fillRight = PlacementPair(AlignPair.FillRight)
        val bottomLeft = PlacementPair(AlignPair.BottomLeft)
        val bottomCenter = PlacementPair(AlignPair.BottomCenter)
        val bottomFill = PlacementPair(AlignPair.BottomFill)
        val bottomRight = PlacementPair(AlignPair.BottomRight)
    }
}