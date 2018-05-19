package lk.kotlin.crossplatform.view.javafx.test

import com.lightningkite.kotlinx.ui.Theme
import com.lightningkite.kotlinx.ui.test.MainVG
import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import lk.kotlin.crossplatform.view.javafx.MaterialJavaFxViewFactory

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val maker = MaterialJavaFxViewFactory(Theme(), resourceFetcher = { javaClass.getResourceAsStream(it) })
        val view = MainVG(maker).generate()
        primaryStage.scene = Scene(view as Parent)
        primaryStage.show()
    }
}

fun main(vararg args: String) {
    Application.launch(Main::class.java)
}