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

fun <VIEW> ViewFactory<VIEW>.margin(
        margin: Float,
        view: VIEW
) = margin(margin, margin, margin, margin, view)

fun <VIEW> ViewFactory<VIEW>.margin(
        horizontal: Float,
        vertical: Float,
        view: VIEW
) = margin(horizontal, vertical, horizontal, vertical, view)

fun <VIEW> ViewFactory<VIEW>.pagesEmbedded(
        page: MutableObservableProperty<Int>,
        vararg pageGenerators: () -> VIEW
) = pages(page, *pageGenerators.map { ViewGenerator.make("", it) }.toTypedArray())