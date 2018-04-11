package com.lightningkite.kotlin.crossplatform.view.old

import lk.kotlin.observable.property.MutableObservableProperty


interface FieldView : View {
    class Style : View.Style()
    companion object {
        val DefaultStyle = Style()
    }


    enum class Type {
        Name,
        Sentence,
        Paragraph,
        Address,
        Phone,
        UppercaseIdentifier,
        Email,
        Password,
        Integer,
        PositiveInteger,
        Float,
        PositiveFloat
    }

    var type: Type
    var hint: String
    val text: MutableObservableProperty<String>
}