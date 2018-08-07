package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.observable.property.transform
import com.lightningkite.kotlinx.ui.builders.image
import com.lightningkite.kotlinx.ui.builders.text
import com.lightningkite.kotlinx.ui.builders.vertical
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.concepts.Animation
import com.lightningkite.kotlinx.ui.concepts.BuiltInSVGs
import com.lightningkite.kotlinx.ui.concepts.TextSize
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.geometry.Point
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

class OriginalTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Original Test"

    val stack = StandardObservableProperty<ViewFactory<VIEW>.() -> VIEW> {
        text(text = "Start")
    }
    var num = 0

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {

        vertical {

            -text(text = "Header", size = TextSize.Header)
            -text(text = "Subheader", size = TextSize.Subheader)
            -text(text = "Body", size = TextSize.Body)
            -text(text = "Tiny", size = TextSize.Tiny)
            -image(BuiltInSVGs.leftChevron(Color.white))
            -image(BuiltInSVGs.leftChevron(Color.white))
            -progress(space(Point(24f, 24f)), ConstantObservableProperty(.5f))
            -work(space(Point(24f, 24f)), ConstantObservableProperty(true))
            -button(label = ConstantObservableProperty("Button"), onClick = {
                stack.value = {
                    num++
                    text(text = "Number $num", size = TextSize.Header, alignPair = AlignPair.CenterCenter)
                }
            })

            +swap(stack.transform {
                val animValues = Animation.values()
                it.invoke(dependency) to animValues[num % animValues.size]
            })

        }
    }
}
