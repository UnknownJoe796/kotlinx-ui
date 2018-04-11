package com.lightningkite.kotlin.crossplatform.view.old

interface ButtonView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    var text: String
    val onClick: MutableCollection<() -> Unit>
}
*/