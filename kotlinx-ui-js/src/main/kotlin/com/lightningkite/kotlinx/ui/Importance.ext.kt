package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.ui.concepts.Importance

fun Importance.toCssClass() = when (this) {
    Importance.Low -> "ImportanceLow"
    Importance.Normal -> "ImportanceNormal"
    Importance.High -> "ImportanceHigh"
    Importance.Danger -> "ImportanceDanger"
}