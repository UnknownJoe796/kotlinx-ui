package com.lightningkite.kotlinx.ui.android

import android.graphics.drawable.Drawable
import com.larvalabs.svgandroid.SVG
import com.larvalabs.svgandroid.SVGBuilder
import com.lightningkite.kotlinx.ui.Image

private val SVGCache = HashMap<String, SVG>()
fun Image.android(): Drawable = when (this) {
    is Image.Bundled -> TODO()
    is Image.Url -> TODO()
    is Image.File -> TODO()
    is Image.EmbeddedSVG -> SVGCache.getOrPut(this.data) {
        SVGBuilder().readFromString(this.data).build()
    }.drawable
}