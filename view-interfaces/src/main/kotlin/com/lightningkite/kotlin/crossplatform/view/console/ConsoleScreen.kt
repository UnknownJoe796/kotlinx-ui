package com.lightningkite.kotlin.crossplatform.view.console

import com.lightningkite.kotlin.crossplatform.view.old.*
import java.util.*
import kotlin.reflect.KClass

class ConsoleScreen() : CanRequestRender {

    var view: ConsoleView = TextConsole(this)

    val generators = HashMap<KClass<*>, () -> ConsoleView>()

    init {
        generators[FieldView::class] = { FieldConsole(this) }
        generators[ContainerView::class] = { ContainerConsole(this) }
        generators[ButtonView::class] = { ButtonConsole(this) }
        generators[TextView::class] = { TextConsole(this) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : View> create(type: KClass<T>): T = generators[type]!!.invoke() as T

    val identifiers = HashMap<String, ConsoleView>()
    fun requestIdentifier(view: ConsoleView, name: String): String {
        @Suppress("UNREACHABLE_CODE")
        val id = run {
            for (index in 1..name.length) {
                val potentialId = name.substring(0, index)
                if (potentialId !in identifiers.keys)
                    return@run potentialId
            }
            var count = 2
            while (true) {
                val potentialId = name + count.toString()
                if (potentialId !in identifiers.keys)
                    return@run potentialId
                count++
            }
            throw IllegalArgumentException()
        }
        identifiers[id] = view
        return id
    }

    fun loop() {
        val scanner = Scanner(System.`in`)
        while (true) {
            render()

            val input = scanner.nextLine()
            val divPoint = input.indexOf(' ')
            if (divPoint != -1) {
                val id = input.substring(0, divPoint).toLowerCase()
                val call = input.substring(divPoint + 1)
                identifiers[id]?.parseInput(call)
            } else {
                identifiers[input]?.parseInput("")
            }
        }
    }

    fun render() {
        identifiers.clear()
        System.out.appendln()
        System.out.appendln()
        view.write(this, TabAppendable(System.out))
    }

    override fun requestRender() {
    }
}

