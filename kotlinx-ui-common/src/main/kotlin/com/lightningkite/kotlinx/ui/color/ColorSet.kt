package com.lightningkite.kotlinx.ui.color

import com.lightningkite.kotlinx.ui.concepts.Importance

data class ColorSet(
        val background: Color = Color.white,
        val backgroundDisabled: Color = background.copy(alpha = background.alpha * 3 / 4),
        val backgroundHighlighted: Color = background.highlight(.2f),
        val foreground: Color = Color.gray(.2f),
        val foregroundDisabled: Color = foreground.copy(alpha = foreground.alpha * 3 / 4),
        val foregroundHighlighted: Color = foreground.highlight(.2f)
) {

    fun importance(value: Importance): Color = when (value) {
        Importance.Low -> foregroundDisabled
        Importance.Normal -> foreground
        Importance.High -> foregroundHighlighted
        Importance.Danger -> destructive.foreground
    }

    companion object {
        fun basedOnBack(color: Color) = ColorSet(
                background = color,
                foreground = if (color.average > .7f) Color.black else Color.white
        )

        val destructive = basedOnBack(Color.red)
    }
}