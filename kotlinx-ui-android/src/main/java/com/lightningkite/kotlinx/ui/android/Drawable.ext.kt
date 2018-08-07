package com.lightningkite.kotlinx.ui.android

import android.graphics.drawable.Drawable
import com.larvalabs.svgandroid.SVG
import com.larvalabs.svgandroid.SVGBuilder
import com.lightningkite.kotlinx.ui.concepts.Image

private val NumberRegex = Regex("\\d+\\.?\\d*")
private val SVGCache = HashMap<String, SVG>()
fun Image.android(): Drawable = when (this) {
    is Image.Bundled -> TODO()
    is Image.Url -> TODO()
    is Image.File -> TODO()
    is Image.EmbeddedSVG -> SVGCache.getOrPut(this.data) {
        SVGBuilder().readFromString(
                this.data.let {
                    NumberRegex.replace(it) {
                        it.value.toDouble().times(dip).toString()
                    }
                }
        ).build()
    }.drawable
}.apply {
    this@android.defaultSize?.let {
        setBounds(0, 0, (it.x * dip).toInt(), (it.y * dip).toInt())
    }
}