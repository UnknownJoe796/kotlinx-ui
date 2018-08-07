package com.lightningkite.kotlinx.ui.implementationhelpers

import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.*
import com.lightningkite.kotlinx.ui.builders.*
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.color.Theme
import com.lightningkite.kotlinx.ui.concepts.*
import com.lightningkite.kotlinx.ui.geometry.Align
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.geometry.Point
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator
import com.lightningkite.kotlinx.ui.withAnimations

fun <VIEW> ViewFactory<VIEW>.defaultEntryContext(
        label: String,
        help: String?,
        icon: Image?,
        feedback: ObservableProperty<Pair<Importance, String>?>,
        field: VIEW
) = horizontal {

    defaultAlign = Align.Center

    if (icon != null) {
        -(image(ConstantObservableProperty(icon))).margin(2f)

        -space(4f)
    }

    +vertical {
        -text(ConstantObservableProperty(label), size = TextSize.Tiny).margin(2f)
        -field.margin(2f)
        -swap(
                feedback.transform {
                    val v = if (it == null) space(Point(12f, 12f))
                    else text(ConstantObservableProperty(it.second), importance = it.first, size = TextSize.Tiny)
                    v to Animation.Fade
                }
        ).margin(2f)
    }
}.margin(6f)


fun <DEPENDENCY, VIEW> ViewFactory<VIEW>.defaultLargeWindow(
        theme: Theme,
        barBuilder: ViewFactory<VIEW>,
        dependency: DEPENDENCY,
        stack: StackObservableProperty<ViewGenerator<DEPENDENCY, VIEW>>,
        tabs: List<Pair<TabItem, ViewGenerator<DEPENDENCY, VIEW>>>,
        actions: ObservableList<Pair<TabItem, () -> Unit>>
) = vertical {
    -with(barBuilder) {
        horizontal {
            defaultAlign = Align.Center
            -imageButton(
                    image = ConstantObservableProperty(BuiltInSVGs.back(theme.bar.foreground)),
                    onClick = { stack.popOrFalse() }
            ).alpha(stack.transform { if (stack.stack.size > 1) 1f else 0f })

            -text(text = stack.transform { it.title }, size = TextSize.Header)

            +space(Point(5f, 5f))

            -swap(actions.onUpdate.transform {
                horizontal {
                    defaultAlign = Align.Center
                    for (item in it) {
                        -button(item.first.text, item.first.image) { item.second.invoke() }
                    }
                } to Animation.Fade
            })
        }.background(theme.bar.background)
    }

    if (tabs.isEmpty()) {
        +swap(stack.withAnimations().transform { it.first.generate(dependency) to it.second })
                .background(theme.main.background)
    } else {
        +horizontal {
            -scroll(vertical {
                for (tab in tabs) {
                    -button(tab.first.text, tab.first.image) {
                        stack.reset(tab.second)
                    }
                }
            })
            +swap(
                    stack.withAnimations().transform { it.first.generate(dependency) to it.second }
            ).background(theme.main.background)

        }
    }
}

fun <DEPENDENCY, VIEW> ViewFactory<VIEW>.defaultSmallWindow(
        theme: Theme,
        barBuilder: ViewFactory<VIEW>,
        dependency: DEPENDENCY,
        stack: StackObservableProperty<ViewGenerator<DEPENDENCY, VIEW>>,
        tabs: List<Pair<TabItem, ViewGenerator<DEPENDENCY, VIEW>>>,
        actions: ObservableList<Pair<TabItem, () -> Unit>>
) = vertical {
    -with(barBuilder) {
        horizontal {
            defaultAlign = Align.Center
            -imageButton(
                    image = ConstantObservableProperty(BuiltInSVGs.back(theme.bar.foreground)),
                    onClick = { stack.popOrFalse() }
            ).alpha(stack.transform { if (stack.stack.size > 1) 1f else 0f })

            -text(text = stack.transform { it.title }, size = TextSize.Header)

            +space(Point(5f, 5f))

            -swap(actions.onUpdate.transform {
                horizontal {
                    defaultAlign = Align.Center
                    for (item in it) {
                        -button(item.first.text, item.first.image) { item.second.invoke() }
                    }
                } to Animation.Fade
            })
        }.background(theme.bar.background)
    }

    +swap(stack.withAnimations().transform { it.first.generate(dependency) to it.second })
            .background(theme.main.background)

    if (!tabs.isEmpty()) {
        -horizontal {
            for (tab in tabs) {
                +button(tab.first.text, tab.first.image) {
                    stack.reset(tab.second)
                }
            }
        }
    }
}


fun <DEPENDENCY, VIEW> ViewFactory<VIEW>.defaultPages(
        buttonColor: Color,
        dependency: DEPENDENCY,
        page: MutableObservableProperty<Int>,
        vararg pageGenerator: ViewGenerator<DEPENDENCY, VIEW>
) = vertical {
    var previous = page.value
    +swap(page.transform {
        val anim = when {
            page.value < previous -> Animation.Pop
            page.value > previous -> Animation.Push
            else -> Animation.Fade
        }
        previous = it
        pageGenerator[it.coerceIn(pageGenerator.indices)].generate(dependency) to anim
    })
    -frame {
        AlignPair.BottomLeft - imageButton(image = ConstantObservableProperty(BuiltInSVGs.leftChevron(buttonColor)), onClick = {
            page.value = page.value.minus(1).coerceIn(pageGenerator.indices)
        })
        AlignPair.BottomCenter - text(text = page.transform { "${it + 1} / ${pageGenerator.size}" }, size = TextSize.Tiny)
        AlignPair.BottomRight - imageButton(image = ConstantObservableProperty(BuiltInSVGs.rightChevron(buttonColor)), onClick = {
            page.value = page.value.plus(1).coerceIn(pageGenerator.indices)
        })
    }
}