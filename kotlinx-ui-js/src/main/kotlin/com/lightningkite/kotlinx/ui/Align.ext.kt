package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.ui.geometry.Align

fun Align.toWeb() = when (this) {
    Align.Start -> "flex-start"
    Align.Center -> "center"
    Align.End -> "flex-end"
    Align.Fill -> "stretch"
}