package com.lightningkite.kotlin.crossplatform.view

import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import lk.kotlin.crossplatform.view.javafx.asJavaFX
import org.junit.Test


class BasicTest {
    @Test
    fun test() {
        val list = observableListOf(1, 2, 3)
        val javaFx = list.asJavaFX(ConstantObservableProperty(true))
        assert(javaFx.size == list.size)
    }
}