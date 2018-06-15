package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.helper.BuiltInSVGs

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
        image: Image? = null,
        onClick: () -> Unit
): VIEW = button(label = ConstantObservableProperty(label), image = ConstantObservableProperty(image), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.imageButton(
        image: Image,
        label: String? = null,
        onClick: () -> Unit
): VIEW = imageButton(label = ConstantObservableProperty(label), image = ConstantObservableProperty(image), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.pagesEmbedded(
        page: MutableObservableProperty<Int>,
        vararg pageGenerators: () -> VIEW
) = pages(page, *pageGenerators.map { ViewGenerator.make("", it) }.toTypedArray())

fun <VIEW> ViewFactory<VIEW>.workButton(
        image: ObservableProperty<Image> = ConstantObservableProperty(BuiltInSVGs.back(Color.white)),
        label: ObservableProperty<String?> = ConstantObservableProperty(null),
        working: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onClick: (MutableObservableProperty<Boolean>) -> Unit
) = work(
        view = imageButton(label = label, image = image, onClick = {
            onClick.invoke(working)
        }),
        isWorking = working
)

fun <VIEW> ViewFactory<VIEW>.progressButton(
        image: ObservableProperty<Image> = ConstantObservableProperty(BuiltInSVGs.back(Color.white)),
        label: ObservableProperty<String?> = ConstantObservableProperty(null),
        progress: MutableObservableProperty<Float> = StandardObservableProperty(1f),
        onClick: (MutableObservableProperty<Float>) -> Unit
) = progress(
        view = imageButton(label = label, image = image, onClick = {
            onClick.invoke(progress)
        }),
        progress = progress
)

fun <VIEW> ViewFactory<VIEW>.workButton(
        image: Image,
        label: String? = null,
        working: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onClick: (MutableObservableProperty<Boolean>) -> Unit
) = work(
        view = imageButton(
                label = ConstantObservableProperty(label),
                image = ConstantObservableProperty(image),
                onClick = {
                    onClick.invoke(working)
                }
        ),
        isWorking = working
)

fun <VIEW> ViewFactory<VIEW>.progressButton(
        image: Image,
        label: String? = null,
        progress: MutableObservableProperty<Float> = StandardObservableProperty(1f),
        onClick: (MutableObservableProperty<Float>) -> Unit
) = progress(
        view = imageButton(
                label = ConstantObservableProperty(label),
                image = ConstantObservableProperty(image),
                onClick = {
                    onClick.invoke(progress)
                }
        ),
        progress = progress
)

fun <VIEW> ViewFactory<VIEW>.workImageButton(
        image: ObservableProperty<Image> = ConstantObservableProperty(BuiltInSVGs.back(Color.white)),
        label: ObservableProperty<String?> = ConstantObservableProperty(null),
        working: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onClick: (MutableObservableProperty<Boolean>) -> Unit
) = work(
        view = imageButton(label = label, image = image, onClick = {
            onClick.invoke(working)
        }),
        isWorking = working
)

fun <VIEW> ViewFactory<VIEW>.progressImageButton(
        image: ObservableProperty<Image> = ConstantObservableProperty(BuiltInSVGs.back(Color.white)),
        label: ObservableProperty<String?> = ConstantObservableProperty(null),
        progress: MutableObservableProperty<Float> = StandardObservableProperty(1f),
        onClick: (MutableObservableProperty<Float>) -> Unit
) = progress(
        view = imageButton(label = label, image = image, onClick = {
            onClick.invoke(progress)
        }),
        progress = progress
)

fun <VIEW> ViewFactory<VIEW>.workImageButton(
        image: Image,
        label: String? = null,
        working: MutableObservableProperty<Boolean> = StandardObservableProperty(false),
        onClick: (MutableObservableProperty<Boolean>) -> Unit
) = work(
        view = imageButton(
                label = ConstantObservableProperty(label),
                image = ConstantObservableProperty(image),
                onClick = {
                    onClick.invoke(working)
                }
        ),
        isWorking = working
)

fun <VIEW> ViewFactory<VIEW>.progressImageButton(
        image: Image,
        label: String? = null,
        progress: MutableObservableProperty<Float> = StandardObservableProperty(1f),
        onClick: (MutableObservableProperty<Float>) -> Unit
) = progress(
        view = imageButton(
                label = ConstantObservableProperty(label),
                image = ConstantObservableProperty(image),
                onClick = {
                    onClick.invoke(progress)
                }
        ),
        progress = progress
)