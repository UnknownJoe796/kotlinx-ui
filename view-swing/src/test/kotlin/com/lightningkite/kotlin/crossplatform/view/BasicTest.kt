package com.lightningkite.kotlin.crossplatform.view

import com.lightningkite.kotlinx.ui.*
import lk.kotlin.crossplatform.view.swing.SwingViewFactory
import lk.kotlin.crossplatform.view.swing.lifecycle
import org.junit.Test
import javax.swing.JFrame

class BasicTest {
    @Test
    fun test() {
        JFrame.setDefaultLookAndFeelDecorated(true)

        JFrame("Hello world!").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            val viewFactory = SwingViewFactory()
            contentPane.add(viewFactory.makeLayout().apply { lifecycle.on() })
            pack()
            isVisible = true
        }

        while (true) {
        }
    }

    fun <T> ViewFactory<T>.makeLayout(): T = vertical(
            PlacementPair(Placement.fill, Placement.wrapStart) to header("Hello world!"),
            PlacementPair(Placement.wrapCenter, Placement.fill) to horizontal(
                    PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to body("First"),
                    PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to body("Second"),
                    PlacementPair(Placement.wrapCenter, Placement.wrapCenter) to body("Third")
            ),
            PlacementPair(Placement.fill, Placement.wrapEnd) to body("This is the first test of the layout manager.")
    )
}