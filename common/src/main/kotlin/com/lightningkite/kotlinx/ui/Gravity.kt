package com.lightningkite.kotlinx.ui


/*

Options:

- Style type per view
- Styles are untyped

- Styles are not cross-platform
- Styles are partially cross-platform

 */

enum class HorizontalGravity { Left, Center, Fill, Right, Start, End }
enum class VerticalGravity { Top, Center, Fill, Bottom }
enum class Gravity(val horizontal: HorizontalGravity, val vertical: VerticalGravity) {
    TopLeft(horizontal = HorizontalGravity.Left, vertical = VerticalGravity.Top),
    TopCenter(horizontal = HorizontalGravity.Center, vertical = VerticalGravity.Top),
    TopFill(horizontal = HorizontalGravity.Fill, vertical = VerticalGravity.Top),
    TopRight(horizontal = HorizontalGravity.Right, vertical = VerticalGravity.Top),
    TopStart(horizontal = HorizontalGravity.Start, vertical = VerticalGravity.Top),
    TopEnd(horizontal = HorizontalGravity.End, vertical = VerticalGravity.Top),

    CenterLeft(horizontal = HorizontalGravity.Left, vertical = VerticalGravity.Center),
    Center(horizontal = HorizontalGravity.Center, vertical = VerticalGravity.Center),
    CenterFill(horizontal = HorizontalGravity.Fill, vertical = VerticalGravity.Center),
    CenterRight(horizontal = HorizontalGravity.Right, vertical = VerticalGravity.Center),
    CenterStart(horizontal = HorizontalGravity.Start, vertical = VerticalGravity.Center),
    CenterEnd(horizontal = HorizontalGravity.End, vertical = VerticalGravity.Center),

    FillLeft(horizontal = HorizontalGravity.Left, vertical = VerticalGravity.Fill),
    FillCenter(horizontal = HorizontalGravity.Center, vertical = VerticalGravity.Fill),
    Fill(horizontal = HorizontalGravity.Fill, vertical = VerticalGravity.Fill),
    FillRight(horizontal = HorizontalGravity.Right, vertical = VerticalGravity.Fill),
    FillStart(horizontal = HorizontalGravity.Start, vertical = VerticalGravity.Fill),
    FillEnd(horizontal = HorizontalGravity.End, vertical = VerticalGravity.Fill),

    BottomLeft(horizontal = HorizontalGravity.Left, vertical = VerticalGravity.Bottom),
    BottomCenter(horizontal = HorizontalGravity.Center, vertical = VerticalGravity.Bottom),
    BottomFill(horizontal = HorizontalGravity.Fill, vertical = VerticalGravity.Bottom),
    BottomRight(horizontal = HorizontalGravity.Right, vertical = VerticalGravity.Bottom),
    BottomStart(horizontal = HorizontalGravity.Start, vertical = VerticalGravity.Bottom),
    BottomEnd(horizontal = HorizontalGravity.End, vertical = VerticalGravity.Bottom),
}