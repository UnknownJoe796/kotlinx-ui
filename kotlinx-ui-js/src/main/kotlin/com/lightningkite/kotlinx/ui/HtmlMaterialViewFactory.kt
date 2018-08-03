package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.Time
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.observable.property.lifecycle.bind
import com.lightningkite.kotlinx.observable.property.transform
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.helper.BuiltInSVGs
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.dom.addClass


class HtmlMaterialViewFactory(
        override val theme: Theme,
        override val colorSet: ColorSet
) : ViewFactory<HTMLElement> {

    override fun withColorSet(colorSet: ColorSet): ViewFactory<HTMLElement> = HtmlMaterialViewFactory(theme, colorSet)

    override fun <DEPENDENCY> window(dependency: DEPENDENCY, stack: StackObservableProperty<ViewGenerator<DEPENDENCY, HTMLElement>>, tabs: List<Pair<TabItem, ViewGenerator<DEPENDENCY, HTMLElement>>>, actions: ObservableList<Pair<TabItem, () -> Unit>>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <DEPENDENCY> pages(dependency: DEPENDENCY, page: MutableObservableProperty<Int>, vararg pageGenerator: ViewGenerator<DEPENDENCY, HTMLElement>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun tabs(options: ObservableList<TabItem>, selected: MutableObservableProperty<TabItem>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> list(data: ObservableList<T>, onBottom: () -> Unit, makeView: (obs: ObservableProperty<T>) -> HTMLElement): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun text(
            text: ObservableProperty<String>,
            importance: Importance,
            size: TextSize,
            align: AlignPair
    ): HTMLElement = when (size) {
        TextSize.Tiny -> document.createElement("p").let { it as HTMLParagraphElement }.apply {
            addClass("TinyText")
            this.align = when (align.horizontal) {
                Align.Start -> "left"
                Align.Center -> "center"
                Align.End -> "right"
                Align.Fill -> "justify"
            }
            lifecycle.bind(text) {
                this.textContent = it
            }
        }
        TextSize.Body -> document.createElement("p").let { it as HTMLParagraphElement }.apply {
            this.align = when (align.horizontal) {
                Align.Start -> "left"
                Align.Center -> "center"
                Align.End -> "right"
                Align.Fill -> "justify"
            }
            lifecycle.bind(text) {
                this.textContent = it
            }
        }
        TextSize.Subheader -> document.createElement("h4").let { it as HTMLHeadingElement }.apply {
            this.align = when (align.horizontal) {
                Align.Start -> "left"
                Align.Center -> "center"
                Align.End -> "right"
                Align.Fill -> "justify"
            }
            lifecycle.bind(text) {
                this.textContent = it
            }
        }
        TextSize.Header -> document.createElement("h1").let { it as HTMLHeadingElement }.apply {
            this.align = when (align.horizontal) {
                Align.Start -> "left"
                Align.Center -> "center"
                Align.End -> "right"
                Align.Fill -> "justify"
            }
            lifecycle.bind(text) {
                this.textContent = it
            }
        }
    }.apply {
        addClass(importance.toCssClass())
        style.verticalAlign = when (align.vertical) {
            Align.Start -> "top"
            Align.Center -> "middle"
            Align.End -> "bottom"
            Align.Fill -> "middle"
        }
    }

    override fun image(image: ObservableProperty<Image>): HTMLImageElement = document.createElement("img")
            .let { it as HTMLImageElement }
            .apply {
                lifecycle.bind(image) {
                    val url = when (it) {
                        is Image.Bundled -> "/${it.identifier}"
                        is Image.Url -> it.url
                        is Image.File -> it.filePath
                        is Image.EmbeddedSVG -> TODO()
                    }
                }
            }

    override fun web(content: ObservableProperty<String>): HTMLElement = TODO()

    override fun space(size: Point): HTMLDivElement = document.createElement("div")
            .let { it as HTMLDivElement }
            .apply {
                this.style.cssText = "width: ${size.x}px; height: ${size.y}px"
            }

    override fun button(
            label: ObservableProperty<String>,
            image: ObservableProperty<Image?>,
            importance: Importance,
            onClick: () -> Unit
    ): HTMLButtonElement = document.createElement("button")
            .let { it as HTMLButtonElement }
            .apply {
                addClass(importance.toCssClass())
                type = "button"

                val textNode: HTMLElement = text(label, importance, align = AlignPair.CenterCenter)
                textNode.lifecycle = lifecycle
                appendChild(textNode)

                val imageNode: HTMLElement by lazy {
                    image(image.transform {
                        it ?: BuiltInSVGs.back(Color.white)
                    })
                }
                var isImageAdded = false
                lifecycle.bind(image) {
                    if (it == null) {
                        if (isImageAdded) {
                            imageNode.lifecycle.parent = null
                            removeChild(imageNode)
                            isImageAdded = false
                        }
                    } else {
                        if (!isImageAdded) {
                            imageNode.lifecycle.parent = lifecycle
                            appendChild(imageNode)
                            isImageAdded = true
                        }
                    }
                }
                onclick = {
                    onClick.invoke()
                }
            }

    override fun imageButton(
            image: ObservableProperty<Image>,
            label: ObservableProperty<String?>,
            importance: Importance,
            onClick: () -> Unit
    ): HTMLButtonElement = document.createElement("button")
            .let { it as HTMLButtonElement }
            .apply {
                addClass(importance.toCssClass())
                type = "button"

                val imageNode: HTMLElement = image(image)
                imageNode.lifecycle = lifecycle
                appendChild(imageNode)

                val textNode: HTMLElement by lazy {
                    text(label.transform {
                        it ?: ""
                    }, importance, align = AlignPair.CenterCenter)
                }
                var isTextAdded = false
                lifecycle.bind(label) {
                    if (it == null) {
                        if (isTextAdded) {
                            textNode.lifecycle.parent = null
                            removeChild(textNode)
                            isTextAdded = false
                        }
                    } else {
                        if (!isTextAdded) {
                            textNode.lifecycle.parent = lifecycle
                            appendChild(textNode)
                            isTextAdded = true
                        }
                    }
                }
                onclick = {
                    onClick.invoke()
                }
            }

    override fun entryContext(label: String, help: String?, icon: Image?, feedback: ObservableProperty<Pair<Importance, String>?>, field: HTMLElement): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> picker(options: ObservableList<T>, selected: MutableObservableProperty<T>, makeView: (obs: ObservableProperty<T>) -> HTMLElement): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun textField(text: MutableObservableProperty<String>, placeholder: String, type: TextInputType): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun textArea(text: MutableObservableProperty<String>, placeholder: String, type: TextInputType): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun numberField(value: MutableObservableProperty<Number?>, placeholder: String, type: NumberInputType, decimalPlaces: Int): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun datePicker(observable: MutableObservableProperty<Date>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dateTimePicker(observable: MutableObservableProperty<DateTime>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun timePicker(observable: MutableObservableProperty<Time>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun slider(range: IntRange, observable: MutableObservableProperty<Int>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toggle(observable: MutableObservableProperty<Boolean>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refresh(contains: HTMLElement, working: ObservableProperty<Boolean>, onRefresh: () -> Unit): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun work(view: HTMLElement, isWorking: ObservableProperty<Boolean>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun progress(view: HTMLElement, progress: ObservableProperty<Float>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scroll(view: HTMLElement): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun swap(view: ObservableProperty<Pair<HTMLElement, Animation>>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun horizontal(
            vararg views: Pair<PlacementPair, HTMLElement>
    ): HTMLElement = document.createElement("div").let { it as HTMLDivElement }.apply {
        style.display = "flex"
        style.flexDirection = "row"
        for ((placement, view) in views) {
            appendChild(view.apply {
                //                when(placement.horizontal.)
            })
        }
    }

    override fun vertical(vararg views: Pair<PlacementPair, HTMLElement>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun frame(vararg views: Pair<PlacementPair, HTMLElement>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun card(view: HTMLElement): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun HTMLElement.margin(left: Float, top: Float, right: Float, bottom: Float): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun HTMLElement.background(color: ObservableProperty<Color>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun HTMLElement.alpha(alpha: ObservableProperty<Float>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun HTMLElement.clickable(onClick: () -> Unit): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}