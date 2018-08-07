package com.lightningkite.kotlinx.ui.android

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import java.util.*


data class DesiredMargins(val left: Int, val top: Int, val right: Int, val bottom: Int)

private val ViewDesiredMargins = WeakHashMap<View, DesiredMargins>()
var View.desiredMargins: DesiredMargins
    get() = ViewDesiredMargins.getOrPut(this) {
        val size = when (this) {
            is CardView -> (8 * dip).toInt()
            is LinearLayout, is FrameLayout, is RecyclerView -> 0
            else -> (8 * dip).toInt()
        }
        return DesiredMargins(size, size, size, size)
    }
    set(value) {
        ViewDesiredMargins[this] = value
    }
private val ViewDesiredHeight = WeakHashMap<View, Int>()
var View.desiredHeight: Int?
    get() = ViewDesiredHeight[this]
    set(value) {
        ViewDesiredHeight[this] = value
    }
private val ViewDesiredWidth = WeakHashMap<View, Int>()
var View.desiredWidth: Int?
    get() = ViewDesiredWidth[this]
    set(value) {
        ViewDesiredWidth[this] = value
    }

var dip = 1f
