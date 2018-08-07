package com.lightningkite.kotlinx.ui.color

interface ThemedViewFactory<out T : ThemedViewFactory<T>> {

    val theme: Theme
    val colorSet: ColorSet

    fun withColorSet(colorSet: ColorSet): T

}