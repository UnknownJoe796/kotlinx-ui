package com.lightningkite.kotlinx.ui

data class Rectangle(
        var x: Float = 0f,
        var y: Float = 0f,
        var width: Float = 0f,
        var height: Float = 0f
) {
    companion object {
        fun fromAlignSize(position: Point, align: Point, size: Point, modify: Rectangle = Rectangle()): Rectangle {
            modify.x = position.x - size.x * align.x
            modify.y = position.y - size.y * align.y
            modify.width = size.x
            modify.height = size.y
            return modify
        }
    }
}