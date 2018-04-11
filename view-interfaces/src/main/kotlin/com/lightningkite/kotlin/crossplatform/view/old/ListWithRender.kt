package com.lightningkite.kotlin.crossplatform.view.old

import lk.kotlin.observable.list.ObservableList
import lk.kotlin.observable.property.MutableObservableProperty

interface ListWithRender<T> {
    val data: ObservableList<T>
    fun create(observable: MutableObservableProperty<T>): View
}