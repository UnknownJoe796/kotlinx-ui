package com.lightningkite.kotlinx.ui

fun Importance.toCssClass() = when (this) {
    Importance.Low -> "ImportanceLow"
    Importance.Normal -> "ImportanceNormal"
    Importance.High -> "ImportanceHigh"
    Importance.Danger -> "ImportanceDanger"
}