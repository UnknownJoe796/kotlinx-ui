package com.lightningkite.kotlinx.ui.android

import android.view.Gravity
import android.view.ViewGroup
import com.lightningkite.kotlinx.ui.Align
import com.lightningkite.kotlinx.ui.AlignPair
import com.lightningkite.kotlinx.ui.Placement


fun Placement.androidSize(dip: Float): Int = if (this.align == Align.Fill) ViewGroup.LayoutParams.MATCH_PARENT else (this.size * dip).toInt()
private val androidGravityMap = mapOf(
        AlignPair.TopLeft to (Gravity.TOP or Gravity.START),
        AlignPair.TopCenter to (Gravity.TOP or Gravity.CENTER_HORIZONTAL),
        AlignPair.TopFill to (Gravity.TOP or Gravity.CENTER_HORIZONTAL),
        AlignPair.TopRight to (Gravity.TOP or Gravity.END),
        AlignPair.CenterLeft to (Gravity.CENTER_VERTICAL or Gravity.START),
        AlignPair.CenterCenter to (Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL),
        AlignPair.CenterFill to (Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL),
        AlignPair.CenterRight to (Gravity.CENTER_VERTICAL or Gravity.END),
        AlignPair.FillLeft to (Gravity.CENTER_VERTICAL or Gravity.START),
        AlignPair.FillCenter to (Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL),
        AlignPair.FillFill to (Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL),
        AlignPair.FillRight to (Gravity.CENTER_VERTICAL or Gravity.END),
        AlignPair.BottomLeft to (Gravity.BOTTOM or Gravity.START),
        AlignPair.BottomCenter to (Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL),
        AlignPair.BottomFill to (Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL),
        AlignPair.BottomRight to (Gravity.BOTTOM or Gravity.END)
)

fun AlignPair.android() = androidGravityMap[this]!!