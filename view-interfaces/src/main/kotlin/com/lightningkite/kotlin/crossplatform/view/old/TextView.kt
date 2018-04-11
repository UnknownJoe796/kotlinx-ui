package com.lightningkite.kotlin.crossplatform.view.old

interface TextView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    var text: String
}