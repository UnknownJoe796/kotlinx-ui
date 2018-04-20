package com.lightningkite.kotlin.crossplatform.view.console

import lk.kotlin.crossplatform.view.*
import lk.kotlin.crossplatform.view.Gravity
import lk.kotlin.crossplatform.view.Image
import lk.kotlin.crossplatform.view.Point
import lk.kotlin.crossplatform.view.ViewFactory
import lk.kotlin.crossplatform.view.virtual.VerticalView
import lk.kotlin.crossplatform.view.virtual.VirtualViewFactory
import lk.kotlin.observable.property.ObservableProperty
import lk.kotlin.observable.property.StandardObservableProperty
import lk.kotlin.observable.property.transform
import org.junit.Test

class TestVirtuals {

    class TestContent(
            var image: Image = object : Image {},
            var title:String = "Title",
            var body:String = "This is some body"
    )

    fun <T> ViewFactory<T>.testView(content:ObservableProperty<TestContent>) = vertical(
            0f,
            Gravity.TopCenter to header(content.transform { it.title }),
            Gravity.TopFill to image(Point(200f, 200f), content.transform { it.image }),
            Gravity.BottomFill to horizontal(
                    0f,
                    Gravity.FillRight to button("Next"){
                        println("Hello next world!")
                    },
                    Gravity.FillLeft to button("Previous"){
                        println("Hello previous world!")
                    }
            ),
            Gravity.Fill to scroll(body(content.transform { it.body }))
    )

    @Test
    fun test(){
        val content = StandardObservableProperty(TestContent())
        val virtualView = VirtualViewFactory.testView(content) as VerticalView
        virtualView.recursiveViews().forEach {
            println(it)
        }
    }
}