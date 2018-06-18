package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty

fun <VIEW> ViewFactory<VIEW>.text(
        size: TextSize = TextSize.Body,
        alignPair: AlignPair = AlignPair.CenterLeft,
        text: String
): VIEW = text(
        text = ConstantObservableProperty(text),
        size = size,
        align = alignPair
)

fun <VIEW> ViewFactory<VIEW>.image(
        image: Image
): VIEW = image(ConstantObservableProperty(image))

fun <VIEW> ViewFactory<VIEW>.space(size: Float): VIEW = space(Point(size, size))
fun <VIEW> ViewFactory<VIEW>.space(width: Float, height: Float): VIEW = space(Point(width, height))

fun <VIEW> ViewFactory<VIEW>.button(
        label: String,
        image: Image? = null,
        onClick: () -> Unit
): VIEW = button(label = ConstantObservableProperty(label), image = ConstantObservableProperty(image), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.imageButton(
        image: Image,
        label: String? = null,
        onClick: () -> Unit
): VIEW = imageButton(label = ConstantObservableProperty(label), image = ConstantObservableProperty(image), onClick = onClick)

fun <DEPENDENCY, VIEW> ViewFactory<VIEW>.pagesEmbedded(
        dependency: DEPENDENCY,
        page: MutableObservableProperty<Int>,
        vararg pageGenerators: (DEPENDENCY) -> VIEW
) = pages(dependency, page, *pageGenerators.map { ViewGenerator.make("", it) }.toTypedArray())