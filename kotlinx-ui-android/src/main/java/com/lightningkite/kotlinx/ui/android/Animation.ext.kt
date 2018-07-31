package com.lightningkite.kotlinx.ui.android

import com.lightningkite.kotlinx.ui.Animation


fun Animation.android(): AnimationSet = when (this) {
    Animation.Push -> AnimationSet.slidePush
    Animation.Pop -> AnimationSet.slidePop
    Animation.MoveUp -> AnimationSet.slideDown
    Animation.MoveDown -> AnimationSet.slideUp
    Animation.Fade -> AnimationSet.fade
    Animation.Flip -> AnimationSet.flipVertical
}