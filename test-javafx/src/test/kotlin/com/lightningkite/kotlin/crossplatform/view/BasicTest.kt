//package com.lightningkite.kotlin.crossplatform.view
//
//import com.jfoenix.concurrency.JFXUtilities
//import com.jfoenix.controls.JFXDecorator
//import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
//import com.lightningkite.kotlinx.observable.property.StackObservableProperty
//import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
//import com.lightningkite.kotlinx.observable.property.transform
//import com.lightningkite.kotlinx.ui.*
//import javafx.application.Application
//import javafx.scene.Parent
//import javafx.scene.Scene
//import javafx.stage.Stage
//import lk.kotlin.crossplatform.view.javafx.MaterialJavaFxViewFactory
//import org.junit.Test
//import java.util.Collections.addAll
//
//
//
//class BasicTest{
//
//    class TestApplication() : Application(){
//
//        fun <T> ViewFactory<T>.frameLayoutTest():T = frame(
//                PlacementPair(Placement.wrapStart, Placement.wrapStart) to text(text = "Top Left", size = TextSize.Body),
//                PlacementPair(Placement.wrapStart, Placement.wrapCenter) to text(text = "Center Left", size = TextSize.Body),
//                PlacementPair(Placement.wrapStart, Placement.wrapEnd) to text(text = "Bottom Left", size = TextSize.Body),
//                PlacementPair(Placement.wrapCenter, Placement.wrapStart) to text(text = "Top Center", size = TextSize.Body),
//                PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to text(text = "Center Center", size = TextSize.Body),
//                PlacementPair(Placement.fill, Placement.fill) to text(text = "Fill", size = TextSize.Header, alignPair = AlignPair.TopLeft),
//                PlacementPair(Placement.wrapCenter, Placement.wrapEnd) to text(text = "Bottom Center", size = TextSize.Body),
//                PlacementPair(Placement.wrapEnd, Placement.wrapStart) to text(text = "Top Right", size = TextSize.Body),
//                PlacementPair(Placement.wrapEnd, Placement.wrapCenter) to text(text = "Center Right", size = TextSize.Body),
//                PlacementPair(Placement.wrapEnd, Placement.wrapEnd) to text(text = "Bottom Right", size = TextSize.Body)
//        )
//
//        fun <T> ViewFactory<T>.verticalLayoutTest():T = vertical(
//                PlacementPair(Placement.wrapStart, Placement.wrapStart) to text(text = "Left Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapCenter, Placement.wrapStart) to text(text = "Center Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Fill Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapEnd, Placement.wrapStart) to text(text = "Right Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapStart, Placement.wrapStart) to text(text = "Left Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapCenter, Placement.wrapStart) to text(text = "Center Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.fill, Placement.fill) to text(text = "Double Fill", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Fill Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapEnd, Placement.wrapStart) to text(text = "Right Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapStart, Placement.wrapStart) to text(text = "Left Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapCenter, Placement.wrapStart) to text(text = "Center Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Fill Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapEnd, Placement.wrapStart) to text(text = "Right Align", size = TextSize.Body, alignPair = AlignPair.CenterCenter)
//        )
//
//        fun <T> ViewFactory<T>.horizontalLayoutTest():T = horizontal(
//                PlacementPair(Placement.wrapStart, Placement.fill) to text(text = "left", alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.fill, Placement.fill) to text(text = "fill", alignPair = AlignPair.CenterCenter),
//                PlacementPair(Placement.wrapStart, Placement.fill) to text(text = "right", alignPair = AlignPair.CenterCenter)
//        )
//
//        fun <T> ViewFactory<T>.makeLayout():T = vertical(
//                PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Hello world!", size = TextSize.Header),
//                PlacementPair(Placement.wrapCenter, Placement.fill) to horizontal(
//                        PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to text(text = "First", size = TextSize.Body),
//                        PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to text(text = "Second", size = TextSize.Body),
//                        PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to text(text = "Third", size = TextSize.Body)
//                ),
//                PlacementPair(Placement.fill, Placement.wrapEnd) to text(text = "This is the first test of the layout manager.", size = TextSize.Body)
//        )
//
//        fun <T> ViewFactory<T>.allTest():T {
//
//            val stack = StandardObservableProperty<()->T>{
//                text(text = "Start")
//            }
//            var num = 0
//
//            return vertical(
//                    PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Header", size = TextSize.Header),
//                    PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Subheader", size = TextSize.Subheader),
//                    PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Body", size = TextSize.Body),
//                    PlacementPair(Placement.fill, Placement.wrapStart) to text(text = "Tiny", size = TextSize.Tiny),
//                    PlacementPair(Placement.fill, Placement.wrapStart) to progress(ConstantObservableProperty(.5f)),
//                    PlacementPair(Placement.fill, Placement.wrapStart) to work(),
//                    PlacementPair(Placement.fill, Placement.wrapStart) to button(label = ConstantObservableProperty("Button"), onClick = {
//
//                        stack.value = {
//                            num++
//                            text(text = "Number $num", size = TextSize.Header, alignPair = AlignPair.CenterCenter)
//                        }
//                    }),
//                    PlacementPair(Placement.fill, Placement.fill) to swap(stack.transform {
//                        val animValues = Animation.values()
//                        it.invoke() to animValues[num % animValues.size]
//                    })
//            )
//        }
//
//        override fun start(primaryStage: Stage) {
//            val maker = MaterialJavaFxViewFactory({javaClass.getResourceAsStream(it)})
////            val view = maker.frameLayoutTest()
////            val view = maker.horizontalLayoutTest()
//            val view = maker.allTest()
////            val view = maker.verticalLayoutTest()
//            primaryStage.scene = Scene(view as Parent)
//            primaryStage.show()
//        }
//    }
//
//    @Test
//    fun test(){
//        Application.launch(TestApplication::class.java)
//
//        while(true){}
//    }
//}