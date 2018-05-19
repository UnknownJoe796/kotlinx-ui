package lk.kotlin.crossplatform.view.javafx

import com.lightningkite.kotlinx.ui.AlignPair
import com.lightningkite.kotlinx.ui.Animation
import com.lightningkite.kotlinx.ui.Point
import com.lightningkite.kotlinx.ui.TextSize
import com.lightningkite.kotlinx.ui.color.Color
import javafx.animation.FadeTransition
import javafx.animation.ScaleTransition
import javafx.animation.Transition
import javafx.animation.TranslateTransition
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.util.Duration

val TextSize.javafx
    get() = when (this) {
        TextSize.Tiny -> 10.0
        TextSize.Body -> 14.0
        TextSize.Subheader -> 18.0
        TextSize.Header -> 24.0
    }
val AlignPair.javafx
    get() = when (this) {
        AlignPair.TopLeft -> Pos.TOP_LEFT
        AlignPair.TopCenter -> Pos.TOP_CENTER
        AlignPair.TopFill -> Pos.TOP_CENTER
        AlignPair.TopRight -> Pos.TOP_RIGHT
        AlignPair.CenterLeft -> Pos.CENTER_LEFT
        AlignPair.CenterCenter -> Pos.CENTER
        AlignPair.CenterFill -> Pos.CENTER
        AlignPair.CenterRight -> Pos.CENTER_RIGHT
        AlignPair.FillLeft -> Pos.CENTER_LEFT
        AlignPair.FillCenter -> Pos.CENTER
        AlignPair.FillFill -> Pos.CENTER
        AlignPair.FillRight -> Pos.CENTER_RIGHT
        AlignPair.BottomLeft -> Pos.BOTTOM_LEFT
        AlignPair.BottomCenter -> Pos.BOTTOM_CENTER
        AlignPair.BottomFill -> Pos.BOTTOM_CENTER
        AlignPair.BottomRight -> Pos.BOTTOM_RIGHT
    }

fun Color.toJavaFX() = javafx.scene.paint.Color.rgb(this.redInt, this.greenInt, this.blueInt, this.alpha.toDouble())

val animationDuration = Duration.millis(300.0)
fun Animation.javaFxOut(node: Node, containerSize: Point): Transition = when (this) {

    Animation.Push -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromX = 0.0
        toX = -containerSize.x.toDouble()
    }
    Animation.Pop -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromX = 0.0
        toX = containerSize.x.toDouble()
    }
    Animation.MoveUp -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromY = 0.0
        toY = containerSize.y.toDouble()
    }
    Animation.MoveDown -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromY = 0.0
        toY = -containerSize.y.toDouble()
    }
    Animation.Fade -> FadeTransition().apply {
        this.node = node
        duration = animationDuration
        fromValue = 1.0
        toValue = 0.0
    }
    Animation.Flip -> ScaleTransition().apply {
        this.node = node
        duration = Duration.millis(animationDuration.toMillis() / 2)
        fromY = 1.0
        toY = 0.0
    }
}

fun Animation.javaFxIn(node: Node, containerSize: Point): Transition = when (this) {

    Animation.Push -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromX = containerSize.x.toDouble()
        toX = 0.0
    }
    Animation.Pop -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromX = -containerSize.x.toDouble()
        toX = 0.0
    }
    Animation.MoveUp -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromY = -containerSize.y.toDouble()
        toY = 0.0
    }
    Animation.MoveDown -> TranslateTransition().apply {
        this.node = node
        duration = animationDuration
        fromY = containerSize.y.toDouble()
        toY = 0.0
    }
    Animation.Fade -> FadeTransition().apply {
        this.node = node
        duration = animationDuration
        fromValue = 0.0
        toValue = 1.0
    }
    Animation.Flip -> ScaleTransition().apply {
        this.node = node
        node.scaleY = 0.0
        delay = Duration.millis(animationDuration.toMillis() / 2)
        duration = Duration.millis(animationDuration.toMillis() / 2)
        fromY = 0.0
        toY = 1.0
    }
}