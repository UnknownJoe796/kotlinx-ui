package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.Time
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.observable.property.lifecycle.bind
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.color.ColorSet
import com.lightningkite.kotlinx.ui.color.Theme
import com.lightningkite.kotlinx.ui.color.ThemedViewFactory
import com.lightningkite.kotlinx.ui.concepts.*
import com.lightningkite.kotlinx.ui.geometry.Align
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.geometry.LinearPlacement
import com.lightningkite.kotlinx.ui.geometry.Point
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.HTMLParagraphElement
import kotlin.browser.document
import kotlin.dom.addClass


class HtmlMaterialViewFactory(
        override val theme: Theme,
        override val colorSet: ColorSet
) : ViewFactory<HTMLElement>, ThemedViewFactory<HtmlMaterialViewFactory> {

    override fun withColorSet(colorSet: ColorSet) = HtmlMaterialViewFactory(theme, colorSet)

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

    override fun image(image: ObservableProperty<Image>): HTMLElement = document.createElement("img")
            .let { it as HTMLImageElement }
            .apply {
            }

    override fun web(content: ObservableProperty<String>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun space(size: Point): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun button(label: ObservableProperty<String>, image: ObservableProperty<Image?>, importance: Importance, onClick: () -> Unit): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun imageButton(image: ObservableProperty<Image>, label: ObservableProperty<String?>, importance: Importance, onClick: () -> Unit): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun horizontal(vararg views: Pair<LinearPlacement, HTMLElement>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun vertical(vararg views: Pair<LinearPlacement, HTMLElement>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun frame(vararg views: Pair<AlignPair, HTMLElement>): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun HTMLElement.setWidth(width: Float): HTMLElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun HTMLElement.setHeight(height: Float): HTMLElement {
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