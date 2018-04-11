package com.lightningkite.kotlin.crossplatform.view.console

@Suppress("NOTHING_TO_INLINE")
class TabAppendable(val wraps: Appendable, var tabLevel: Int = 0) : Appendable by wraps {

    object SystemProperties {
        /** Line separator for current system. */
        @JvmField
        val LINE_SEPARATOR = System.getProperty("line.separator")!!
    }

    inline fun Appendable.appendln(): Appendable = append(SystemProperties.LINE_SEPARATOR).apply {
        repeat(tabLevel) {
            append('\t')
        }
    }

    inline fun Appendable.appendln(value: CharSequence?): Appendable = append(value).append(SystemProperties.LINE_SEPARATOR).apply {
        repeat(tabLevel) {
            append('\t')
        }
    }

    inline fun Appendable.appendln(value: Char): Appendable = append(value).append(SystemProperties.LINE_SEPARATOR).apply {
        repeat(tabLevel) {
            append('\t')
        }
    }
}