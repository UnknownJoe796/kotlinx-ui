package com.lightningkite.kotlinx.ui.helper

import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.*
import com.lightningkite.kotlinx.ui.*

fun <VIEW> ViewFactory<VIEW>.defaultEntryContext(
        label: String,
        help: String?,
        icon: Image?,
        feedback: ObservableProperty<Pair<Importance, String>?>,
        field: VIEW
) = horizontal {

    defaultPlacement = PlacementPair.centerLeft

    if (icon != null) {
        +(image(ConstantObservableProperty(icon))).margin(2f)

        +space(4f)
    }

    PlacementPair.topFill + vertical {
        PlacementPair.topLeft + text(ConstantObservableProperty(label), size = TextSize.Tiny).margin(2f)
        PlacementPair.topFill + field.margin(2f)
        PlacementPair.topFill + swap(
                feedback.transform {
                    val v = if (it == null) space(Point(12f, 12f))
                    else text(ConstantObservableProperty(it.second), importance = it.first, size = TextSize.Tiny)
                    v to Animation.Fade
                }
        ).margin(2f)
    }
}.margin(6f)


fun <DEPENDENCY, VIEW> ViewFactory<VIEW>.defaultLargeWindow(
        dependency: DEPENDENCY,
        stack: StackObservableProperty<ViewGenerator<DEPENDENCY, VIEW>>,
        tabs: List<Pair<TabItem, ViewGenerator<DEPENDENCY, VIEW>>>,
        actions: ObservableList<Pair<TabItem, () -> Unit>>
) = vertical {
    PlacementPair.topFill + with(this@defaultLargeWindow.withColorSet(theme.bar)) {
        horizontal {
            PlacementPair.centerLeft + imageButton(
                    image = ConstantObservableProperty(BuiltInSVGs.back(colorSet.foreground)),
                    onClick = { stack.popOrFalse() }
            ).alpha(stack.transform { if (stack.stack.size > 1) 1f else 0f })

            PlacementPair.centerLeft + text(text = stack.transform { it.title }, size = TextSize.Header)

            PlacementPair.fillFill + space(Point(5f, 5f))

            PlacementPair.centerRight + swap(actions.onUpdate.transform {
                horizontal {
                    for (item in it) {
                        PlacementPair.centerCenter + button(item.first.text, item.first.image) { item.second.invoke() }
                    }
                } to Animation.Fade
            })
        }.background()
    }

    PlacementPair.fillFill +
            if (tabs.isEmpty()) {
                swap(stack.withAnimations().transform { it.first.generate(dependency) to it.second })
                        .background()
            } else {
                horizontal {
                    PlacementPair.fillLeft + scroll(vertical {
                        for (tab in tabs) {
                            PlacementPair.topFill + button(tab.first.text, tab.first.image) {
                                stack.reset(tab.second)
                            }
                        }
                    })
                    PlacementPair.fillFill + swap(
                            stack.withAnimations().transform { it.first.generate(dependency) to it.second }
                    ).background()

                }
            }
}

fun <DEPENDENCY, VIEW> ViewFactory<VIEW>.defaultSmallWindow(
        dependency: DEPENDENCY,
        stack: StackObservableProperty<ViewGenerator<DEPENDENCY, VIEW>>,
        tabs: List<Pair<TabItem, ViewGenerator<DEPENDENCY, VIEW>>>,
        actions: ObservableList<Pair<TabItem, () -> Unit>>
) = vertical {
    PlacementPair.topFill + with(this@defaultSmallWindow.withColorSet(theme.bar)) {
        horizontal {
            PlacementPair.centerLeft + imageButton(
                    image = ConstantObservableProperty(BuiltInSVGs.back(colorSet.foreground)),
                    onClick = { stack.popOrFalse() }
            ).alpha(stack.transform { if (stack.stack.size > 1) 1f else 0f })

            PlacementPair.centerLeft + text(text = stack.transform { it.title }, size = TextSize.Header)

            PlacementPair.fillFill + space(Point(5f, 5f))

            PlacementPair.centerRight + swap(actions.onUpdate.transform {
                horizontal {
                    for (item in it) {
                        PlacementPair.centerCenter + button(item.first.text, item.first.image) { item.second.invoke() }
                    }
                } to Animation.Fade
            })
        }.background()
    }

    PlacementPair.fillFill + swap(stack.withAnimations().transform { it.first.generate(dependency) to it.second })
            .background()

    if (!tabs.isEmpty()) {
        PlacementPair.topFill + horizontal {
            for (tab in tabs) {
                PlacementPair.fillFill + button(tab.first.text, tab.first.image) {
                    stack.reset(tab.second)
                }
            }
        }.background()
    }
}


fun <DEPENDENCY, VIEW> ViewFactory<VIEW>.defaultPages(
        dependency: DEPENDENCY,
        page: MutableObservableProperty<Int>,
        vararg pageGenerator: ViewGenerator<DEPENDENCY, VIEW>
) = vertical(
        PlacementPair.fillFill to run {
            var previous = page.value
            swap(page.transform {
                val anim = when {
                    page.value < previous -> Animation.Pop
                    page.value > previous -> Animation.Push
                    else -> Animation.Fade
                }
                previous = it
                pageGenerator[it.coerceIn(pageGenerator.indices)].generate(dependency) to anim
            })
        },
        PlacementPair.bottomFill to frame(
                PlacementPair.bottomLeft to imageButton(image = ConstantObservableProperty(BuiltInSVGs.leftChevron(colorSet.foreground)), onClick = {
                    page.value = page.value.minus(1).coerceIn(pageGenerator.indices)
                }),
                PlacementPair.bottomCenter to text(text = page.transform { "${it + 1} / ${pageGenerator.size}" }, size = TextSize.Tiny),
                PlacementPair.bottomRight to imageButton(image = ConstantObservableProperty(BuiltInSVGs.rightChevron(colorSet.foreground)), onClick = {
                    page.value = page.value.plus(1).coerceIn(pageGenerator.indices)
                })
        )
)