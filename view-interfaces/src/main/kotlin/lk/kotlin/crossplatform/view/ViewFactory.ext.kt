package lk.kotlin.crossplatform.view

import lk.kotlin.observable.property.ConstantObservableProperty


fun <VIEW> ViewFactory<VIEW>.header(
        text: String
): VIEW = header(ConstantObservableProperty(text))

fun <VIEW> ViewFactory<VIEW>.subheader(
        text: String
): VIEW = subheader(ConstantObservableProperty(text))

fun <VIEW> ViewFactory<VIEW>.body(
        text: String
): VIEW = body(ConstantObservableProperty(text))

fun <VIEW> ViewFactory<VIEW>.button(
        label: String,
        onClick:()->Unit
): VIEW = button(label = ConstantObservableProperty(label), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.button(
        image: Image,
        onClick:()->Unit
): VIEW = button(image = ConstantObservableProperty(image), onClick = onClick)

fun <VIEW> ViewFactory<VIEW>.margin(
        margin:Float,
        view:VIEW
) = margin(margin, margin, margin, margin, view)

fun <VIEW> ViewFactory<VIEW>.margin(
        horizontal:Float,
        vertical:Float,
        view:VIEW
) = margin(horizontal, vertical, horizontal, vertical, view)