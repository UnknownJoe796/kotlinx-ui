package com.lightningkite.kotlin.crossplatform.view.old

interface GridView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    var data: ListWithRender<*>
    var currentPosition: Int
}
