package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.observable.property.transform
import com.lightningkite.kotlinx.ui.*
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.helper.BuiltInSVGs

class OriginalTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Original Test"

    val stack = StandardObservableProperty<ViewFactory<VIEW>.() -> VIEW> {
        text(text = "Start")
    }
    var num = 0

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {

        vertical(
                PlacementPair.topFill to text(text = "Header", size = TextSize.Header),
                PlacementPair.topFill to text(text = "Subheader", size = TextSize.Subheader),
                PlacementPair.topFill to text(text = "Body", size = TextSize.Body),
                PlacementPair.topFill to text(text = "Tiny", size = TextSize.Tiny),
                PlacementPair.topFill to image(BuiltInSVGs.leftChevron(Color.white)),
                PlacementPair(Placement.fill, Placement(Align.Start, 100f)) to image(BuiltInSVGs.leftChevron(Color.white)),
                PlacementPair.topFill to progress(space(Point(24f, 24f)), ConstantObservableProperty(.5f)),
                PlacementPair.topFill to work(space(Point(24f, 24f)), ConstantObservableProperty(true)),
                PlacementPair.topFill to button(label = ConstantObservableProperty("Button"), onClick = {
                    stack.value = {
                        num++
                        text(text = "Number $num", size = TextSize.Header, alignPair = AlignPair.CenterCenter)
                    }
                }),
                PlacementPair.fillFill to swap(stack.transform {
                    val animValues = Animation.values()
                    it.invoke(dependency) to animValues[num % animValues.size]
                })
        )
    }
}
