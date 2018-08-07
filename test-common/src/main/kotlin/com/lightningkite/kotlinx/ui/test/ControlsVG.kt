package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.TimeStamps
import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.builders.vertical
import com.lightningkite.kotlinx.ui.concepts.Importance
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

class ControlsVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Controls"
    val alpha = StandardObservableProperty(0f)

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        scroll(vertical {

            -entryContext(
                    label = "button",
                    field = button(label = ConstantObservableProperty("Button"), importance = Importance.Low, onClick = {})
            )
            -entryContext(
                    label = "button",
                    field = button(label = ConstantObservableProperty("Button"), importance = Importance.Normal, onClick = {})
            )
            -entryContext(
                    label = "button",
                    field = button(label = ConstantObservableProperty("Button"), importance = Importance.High, onClick = {})
            )
            -entryContext(
                    label = "button",
                    field = button(label = ConstantObservableProperty("Button"), importance = Importance.Danger, onClick = {})
            )
            -entryContext(
                    label = "textField",
                    field = textField(text = StandardObservableProperty("TextField"))
            )
            -entryContext(
                    label = "textArea",
                    field = textArea(text = StandardObservableProperty("TextArea"))
            )
            -entryContext(
                    label = "numberField",
                    field = numberField(value = StandardObservableProperty(22))
            )
            -entryContext(
                    label = "slider",
                    field = slider(0..100, StandardObservableProperty(22))
            )
            -entryContext(
                    label = "picker",
                    field = picker(observableListOf("A", "B", "C"), StandardObservableProperty("A")) {
                        text(it)
                    }
            )
            -entryContext(
                    label = "toggle",
                    field = toggle(StandardObservableProperty(false))
            )
            -entryContext(
                    label = "datePicker",
                    field = datePicker(StandardObservableProperty(TimeStamps.now().date()))
            )
            -entryContext(
                    label = "timePicker",
                    field = timePicker(StandardObservableProperty(TimeStamps.now().time()))
            )
            -entryContext(
                    label = "dateTimePicker",
                    field = dateTimePicker(StandardObservableProperty(DateTime(TimeStamps.now().date(), TimeStamps.now().time())))
            )
        })
    }
}
