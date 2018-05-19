package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.ui.color.Color

data class Theme(
        val main: ColorSet = ColorSet(),
        val bar: ColorSet = ColorSet.basedOnBack(Color.fromInt(0xFF6200EE.toInt())),
        val accent: ColorSet = ColorSet.basedOnBack(Color.fromInt(0xFF03DAC6.toInt()))
) {

    companion object {
//        fun light(
//                primaryColor:Color = Color.fromInt(0xFF6200EE.toInt()),
//                secondaryColor:Color = Color.fromInt(0xFF03DAC6.toInt())
//        ) = Theme(
//                ColorSet(),
//        )
    }

}