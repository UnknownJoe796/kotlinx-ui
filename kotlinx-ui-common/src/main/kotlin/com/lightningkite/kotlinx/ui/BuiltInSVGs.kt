package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.concepts.Image
import com.lightningkite.kotlinx.ui.geometry.Point

object BuiltInSVGs {
    fun back(color: Color) = Image.EmbeddedSVG("""<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
    <path d="M0 0h24v24H0z" fill="none"/>
    <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z" fill="${color.toAlphalessWeb()}"/>
</svg>
""", Point(24f, 24f))

    fun leftChevron(color: Color) = Image.EmbeddedSVG("""<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
    <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z" fill="${color.toAlphalessWeb()}"/>
    <path d="M0 0h24v24H0z" fill="none"/>
</svg>
""", Point(24f, 24f))

    fun rightChevron(color: Color) = Image.EmbeddedSVG("""<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
    <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z" fill="${color.toAlphalessWeb()}"/>
    <path d="M0 0h24v24H0z" fill="none"/>
</svg>
""", Point(24f, 24f))
}