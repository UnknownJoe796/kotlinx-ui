package com.lightningkite.kotlinx.ui.concepts

import com.lightningkite.kotlinx.ui.geometry.Point

sealed class Image(
        val defaultSize: Point? = null,
        val scaleType: ImageScaleType = ImageScaleType.Fill
) {
    class Bundled(
            val identifier: String,
            defaultSize: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(defaultSize = defaultSize, scaleType = scaleType)

    class Url(
            val url: String,
            defaultSize: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(defaultSize = defaultSize, scaleType = scaleType)

    class File(
            val filePath: String,
            defaultSize: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(defaultSize = defaultSize, scaleType = scaleType)

    class EmbeddedSVG(
            val data: String,
            defaultSize: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(defaultSize = defaultSize, scaleType = scaleType)
}

