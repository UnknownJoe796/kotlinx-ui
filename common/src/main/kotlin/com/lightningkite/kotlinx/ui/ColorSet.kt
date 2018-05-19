package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.ui.color.Color

data class ColorSet(
        val background: Color = Color.white,
        val backgroundDisabled: Color = background.copy(alpha = background.alpha * 3 / 4),
        val backgroundHighlighted: Color = background.highlight(.2f),
        val foreground: Color = Color.gray(.2f),
        val foregroundDisabled: Color = foreground.copy(alpha = foreground.alpha * 3 / 4),
        val foregroundHighlighted: Color = foreground.highlight(.2f)
) {
    companion object {
        fun basedOnBack(color: Color) = ColorSet(
                background = color,
                foreground = if (color.average > .5f) Color.black else Color.white
        )
    }
}