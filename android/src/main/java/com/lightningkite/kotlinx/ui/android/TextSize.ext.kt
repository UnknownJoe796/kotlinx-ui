package com.lightningkite.kotlinx.ui.android

import com.lightningkite.kotlinx.ui.TextSize


fun TextSize.sp(): Float = when (this) {
    TextSize.Tiny -> 12f
    TextSize.Body -> 16f
    TextSize.Subheader -> 20f
    TextSize.Header -> 24f
}