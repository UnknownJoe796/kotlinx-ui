package com.lightningkite.kotlin.crossplatform.view.old

import com.lightningkite.kotlin.crossplatform.view.Image

interface ImageView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    var image: Image
}