package lk.kotlin.crossplatform.view.javafx.test

import com.lightningkite.kotlinx.ui.Theme
import com.lightningkite.kotlinx.ui.test.MainVG
import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import lk.kotlin.crossplatform.view.javafx.MaterialJavaFxViewFactory
import lk.kotlin.crossplatform.view.javafx.lifecycle

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val maker = MaterialJavaFxViewFactory(Theme.dark(), resourceFetcher = { javaClass.getResourceAsStream(it) }, scale = 2.0)
        val view = MainVG(maker).generate()
        view.lifecycle.alwaysOn = true
        primaryStage.scene = Scene(view as Parent)
        primaryStage.show()
    }
}

fun main(vararg args: String) {
    Application.launch(Main::class.java)
}