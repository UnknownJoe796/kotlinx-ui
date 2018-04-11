package com.lightningkite.kotlin.crossplatform.view.old

interface WorkView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


}