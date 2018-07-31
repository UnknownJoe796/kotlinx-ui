package com.lightningkite.kotlinx.ui

sealed class Image(
        val size: Point? = null,
        val scaleType: ImageScaleType = ImageScaleType.Fill
) {
    class Bundled(
            val identifier: String,
            size: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(size = size, scaleType = scaleType)

    class Url(
            val url: String,
            size: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(size = size, scaleType = scaleType)

    class File(
            val filePath: String,
            size: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(size = size, scaleType = scaleType)

    class EmbeddedSVG(
            val data: String,
            size: Point? = null,
            scaleType: ImageScaleType = ImageScaleType.Fill
    ) : Image(size = size, scaleType = scaleType)
}

