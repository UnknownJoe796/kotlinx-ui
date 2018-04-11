package com.lightningkite.kotlin.crossplatform.view.console


fun PlatformContext.makeView() = container {
    views += text { text = "You can get greeted!" }
    val textField = field {
        hint = "Name"
    }
    views += textField
    views += button {
        text = "Say Hello"
        onClick += {
            println("Hello ${textField.text.value}!")
        }
    }
}

fun main(vararg args: String) {
    ConsoleScreen().apply {
        view = makeView() as ConsoleView
    }.loop()
}