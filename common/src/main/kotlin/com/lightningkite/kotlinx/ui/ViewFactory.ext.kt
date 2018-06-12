package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty

fun <VIEW> ViewFactory<VIEW>.text(
        size: TextSize = TextSize.Body,
        alignPair: AlignPair = AlignPair.CenterLeft,
        text: String
): VIEW = text(
        text = ConstantObservableProperty(text),
        size = size,
        align = alignPair
)

fun <VIEW> ViewFactory<VIEW>.space(size: Float): VIEW = space(Point(size, size))
fun <VIEW> ViewFactory<VIEW>.space(width: Float, height: Float): VIEW = space(Point(width, height))

fun <VIEW> ViewFactory<VIEW>.button(
        label: String,
        onClick: () -> Unit
): VIEW = button(label = ConstantObservableProperty(label), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.button(
        image: Image,
        onClick: () -> Unit
): VIEW = button(image = ConstantObservableProperty(image), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.button(
        label: String,
        image: Image,
        onClick: () -> Unit
): VIEW = button(label = ConstantObservableProperty(label), image = ConstantObservableProperty(image), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.pagesEmbedded(
        page: MutableObservableProperty<Int>,
        vararg pageGenerators: () -> VIEW
) = pages(page, *pageGenerators.map { ViewGenerator.make("", it) }.toTypedArray())

fun <VIEW> ViewFactory<VIEW>.workButton(
        label: ObservableProperty<String?> = ConstantObservableProperty(null),
        image: ObservableProperty<Image?> = ConstantObservableProperty(null),
        working: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onClick: (MutableObservableProperty<Boolean>) -> Unit
) = work(
        view = button(label = label, image = image, onClick = {
            onClick.invoke(working)
        }),
        isWorking = working
)

fun <VIEW> ViewFactory<VIEW>.progressButton(
        label: ObservableProperty<String?> = ConstantObservableProperty(null),
        image: ObservableProperty<Image?> = ConstantObservableProperty(null),
        progress: MutableObservableProperty<Float> = StandardObservableProperty(1f),
        onClick: (MutableObservableProperty<Float>) -> Unit
) = progress(
        view = button(label = label, image = image, onClick = {
            onClick.invoke(progress)
        }),
        progress = progress
)

fun <VIEW> ViewFactory<VIEW>.workButton(
        label: String? = null,
        image: Image? = null,
        working: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onClick: (MutableObservableProperty<Boolean>) -> Unit
) = work(
        view = button(
                label = ConstantObservableProperty(label),
                image = ConstantObservableProperty(image),
                onClick = {
                    onClick.invoke(working)
                }
        ),
        isWorking = working
)

fun <VIEW> ViewFactory<VIEW>.progressButton(
        label: String? = null,
        image: Image? = null,
        progress: MutableObservableProperty<Float> = StandardObservableProperty(1f),
        onClick: (MutableObservableProperty<Float>) -> Unit
) = progress(
        view = button(
                label = ConstantObservableProperty(label),
                image = ConstantObservableProperty(image),
                onClick = {
                    onClick.invoke(progress)
                }
        ),
        progress = progress
)