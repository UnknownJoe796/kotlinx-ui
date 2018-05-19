package com.lightningkite.kotlinx.ui

interface ViewGenerator<VIEW> {
    val title: String get() = ""
    fun generate(): VIEW

    companion object {
        fun <VIEW> make(title: String, generate: () -> VIEW) = object : ViewGenerator<VIEW> {
            override val title: String = title
            override fun generate(): VIEW = generate.invoke()
        }
    }
}