package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.TimeStamps
import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.PlacementPair
import com.lightningkite.kotlinx.ui.ViewFactory
import com.lightningkite.kotlinx.ui.ViewGenerator
import com.lightningkite.kotlinx.ui.vertical

class ControlsVG<VIEW>(val factory: ViewFactory<VIEW>) : ViewGenerator<VIEW> {
    override val title: String = "Controls"
    val alpha = StandardObservableProperty(0f)

    override fun generate(): VIEW = with(factory) {
        scroll(vertical {
            defaultPlacement = PlacementPair.topFill

            +entryContext(
                    label = "button",
                    field = button(label = ConstantObservableProperty("Button"), onClick = {})
            )
            +entryContext(
                    label = "textField",
                    field = textField(text = StandardObservableProperty("TextField"))
            )
            +entryContext(
                    label = "textArea",
                    field = textArea(text = StandardObservableProperty("TextArea"))
            )
            +entryContext(
                    label = "numberField",
                    field = numberField(value = StandardObservableProperty(22))
            )
            +entryContext(
                    label = "slider",
                    field = slider(0..100, StandardObservableProperty(22))
            )
            +entryContext(
                    label = "picker",
                    field = picker(observableListOf("A", "B", "C"), StandardObservableProperty("A"), {
                        text(it)
                    })
            )
            +entryContext(
                    label = "toggle",
                    field = toggle(StandardObservableProperty(false))
            )
            +entryContext(
                    label = "datePicker",
                    field = datePicker(StandardObservableProperty(TimeStamps.now().date()))
            )
            +entryContext(
                    label = "timePicker",
                    field = timePicker(StandardObservableProperty(TimeStamps.now().time()))
            )
            +entryContext(
                    label = "dateTimePicker",
                    field = dateTimePicker(StandardObservableProperty(DateTime(TimeStamps.now().date(), TimeStamps.now().time())))
            )
        })
    }
}
