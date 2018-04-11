package com.lightningkite.kotlin.crossplatform.view.old

interface ProgressView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    var progress: Float
}