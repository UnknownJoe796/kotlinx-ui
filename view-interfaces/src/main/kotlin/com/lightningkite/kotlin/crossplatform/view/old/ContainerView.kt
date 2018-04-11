package com.lightningkite.kotlin.crossplatform.view.old

interface ContainerView : View, HasViewList {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    override val views: MutableList<View>
}

