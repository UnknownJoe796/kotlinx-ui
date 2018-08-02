package com.lightningkite.kotlinx.ui.views

import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.Time
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.concepts.*
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.geometry.LinearPlacement
import com.lightningkite.kotlinx.ui.geometry.Point

/**
 * PHILOSOPHY
 *
 * The views will function and appear according to the underlying platform.  Styling does
 * not take place at this interface layer.
 *
 * Thus, every function here is meant to represent a concept rather than a specific widget.
 *
 * This interface is meant to be extended and added upon, and only represents the most basic of
 * views needed for creating an app.
 *
 * Layout does take place at this layer, and is meant to be good at resizing.
 *
 * All views are automatically sized unless stated otherwise - either shrinking as small as possible
 * or growing as large as possible.
 *
 * The defaults for spacing are set to look both clean and good - modify them deliberately.
 *
 * The returned view objects are only meant to be used in composing with other views in the factory.
 * Do not attempt to store references to them long-term or anything of the sort.
 */
interface ViewFactory<VIEW> {

    //Navigation

    /**
     * The main window of the program - provides a stack, navigation, tab navigation, and actions.
     */
    fun <DEPENDENCY> window(
            dependency: DEPENDENCY,
            stack: StackObservableProperty<ViewGenerator<DEPENDENCY, VIEW>>,
            tabs: List<Pair<TabItem, ViewGenerator<DEPENDENCY, VIEW>>>,
            actions: ObservableList<Pair<TabItem, () -> Unit>>
    ): VIEW

    /**
     * A set of ordered pages you can swap through with the built-in navigator.
     */
    fun <DEPENDENCY> pages(
            dependency: DEPENDENCY,
            page: MutableObservableProperty<Int>,
            vararg pageGenerator: ViewGenerator<DEPENDENCY, VIEW>
    ): VIEW

    /**
     * A set of tabs, with one selected.
     * Does not modify any views outside of it - hook it up to a [swap] for that functionality.
     */
    fun tabs(
            options: ObservableList<TabItem>,
            selected: MutableObservableProperty<TabItem>
    ): VIEW


    //Collection Views

    /**
     * Shows a list of items and notifies you when it's scrolled to the bottom.
     */
    fun <T> list(
            data: ObservableList<T>,
            onBottom: () -> Unit,
            makeView: (obs: ObservableProperty<T>) -> VIEW
    ): VIEW


    //Display

    /**
     * Shows a piece of text at the given size.
     */
    fun text(
            text: ObservableProperty<String>,
            importance: Importance = Importance.Normal,
            size: TextSize = TextSize.Body,
            align: AlignPair = AlignPair.CenterLeft
    ): VIEW

    /**
     * Shows an image with the given scaling.
     * While loading, it will show a loading indicator.
     */
    fun image(
            image: ObservableProperty<Image>
    ): VIEW

    /**
     * Shows a webpage within this view.
     * If the string starts with 'http://', it will be interpreted as a URL.
     * Otherwise, it will be interpreted as non-interactive HTML content.
     */
    fun web(
            content: ObservableProperty<String>
    ): VIEW

    /**
     * Creates a blank space.
     */
    fun space(size: Point): VIEW


    //Activation Controls

    /**
     * A button with the given image and label.  Runs [onClick] when the button is interacted with.
     */
    fun button(
            label: ObservableProperty<String>,
            image: ObservableProperty<Image?> = ConstantObservableProperty(null),
            importance: Importance = Importance.Normal,
            onClick: () -> Unit
    ): VIEW

    /**
     * A button with the given image and label.  Runs [onClick] when the button is interacted with.
     * Attempts to use image over text.
     */
    fun imageButton(
            image: ObservableProperty<Image>,
            label: ObservableProperty<String?> = ConstantObservableProperty(null),
            importance: Importance = Importance.Normal,
            onClick: () -> Unit
    ): VIEW


    //Entry Controls

    /**
     * Perhaps the most complex part - a wrapper around controls that gives them context.
     * @param label A label for the given field.
     * @param help A helpful description of what is to be entered here.  Showed only through interaction.
     * @param icon An icon that represents the field.
     * @param feedback Feedback for the user.
     * @param field The field itself.
     */
    fun entryContext(
            label: String,
            help: String? = null,
            icon: Image? = null,
            feedback: ObservableProperty<Pair<Importance, String>?> = ConstantObservableProperty(null),
            field: VIEW
    ): VIEW

    /**
     * An element that allows the user to select an item from a list.
     */
    fun <T> picker(
            options: ObservableList<T>,
            selected: MutableObservableProperty<T>,
            makeView: (obs: ObservableProperty<T>) -> VIEW
    ): VIEW

    /**
     * An element that allows the user to enter a small piece of text.
     */
    fun textField(
            text: MutableObservableProperty<String>,
            placeholder: String = "",
            type: TextInputType = TextInputType.Name
    ): VIEW

    /**
     * An element that allows the user to enter a large piece of text.
     */
    fun textArea(
            text: MutableObservableProperty<String>,
            placeholder: String = "",
            type: TextInputType = TextInputType.Paragraph
    ): VIEW

    /**
     * An element that allows the user to enter a number.
     */
    fun numberField(
            value: MutableObservableProperty<Number?>,
            placeholder: String = "",
            type: NumberInputType = NumberInputType.PositiveInteger,
            decimalPlaces: Int = 0
    ): VIEW

    /**
     * An element that allows the user to enter a date.
     */
    fun datePicker(
            observable: MutableObservableProperty<Date>
    ): VIEW

    /**
     * An element that allows the user to enter a date and time.
     */
    fun dateTimePicker(
            observable: MutableObservableProperty<DateTime>
    ): VIEW

    /**
     * An element that allows the user to enter a time.
     */
    fun timePicker(
            observable: MutableObservableProperty<Time>
    ): VIEW

    /**
     * A slider that lets the user pick a value within the given range.
     */
    fun slider(
            range: IntRange,
            observable: MutableObservableProperty<Int>
    ): VIEW

    /**
     * A switch or checkbox (depending on platform) that the user can turn on or off.
     */
    fun toggle(
            observable: MutableObservableProperty<Boolean>
    ): VIEW


    //Containers

    /**
     * Wraps content with a control to refresh the data contained within.
     * On mobile, enables a "swipe to refresh" gesture.
     */
    fun refresh(
            contains: VIEW,
            working: ObservableProperty<Boolean>,
            onRefresh: () -> Unit
    ): VIEW

    /**
     * Shows the given view if not working, otherwise shows a working indicator.
     */
    fun work(
            view: VIEW,
            isWorking: ObservableProperty<Boolean>
    ): VIEW

    /**
     * Shows the given view if not working, otherwise shows a progress indicator.
     * @param progress A number 0-1 showing the amount of progress made on a task.  When the value is 1,
     */
    fun progress(
            view: VIEW,
            progress: ObservableProperty<Float>
    ): VIEW

    /**
     * Wraps content to make it scroll vertically.
     */
    fun scroll(
            view: VIEW
    ): VIEW

    /**
     * Shows a single view at a time, which can be switched out with another through animation.
     */
    fun swap(
            view: ObservableProperty<Pair<VIEW, Animation>>
    ): VIEW

    /**
     * Places elements horizontally, left to right.
     * The placement pairs determine whether or not the elements are stretched or shifted around.
     */
    fun horizontal(
            vararg views: Pair<LinearPlacement, VIEW>
    ): VIEW

    /**
     * Places elements vertically, top to bottom.
     * The placement pairs determine whether or not the elements are stretched or shifted around.
     */
    fun vertical(
            vararg views: Pair<LinearPlacement, VIEW>
    ): VIEW

    /**
     * Places elements on top of each other, back to front.
     * The placement pairs determine whether or not the elements are stretched or shifted around.
     */
    fun frame(
            vararg views: Pair<AlignPair, VIEW>
    ): VIEW

    /**
     * Adds a 'card' background to the given item.
     */
    fun card(view: VIEW): VIEW


    //Modifiers

    /**
     * Adds a margin around an item.
     */
    fun VIEW.margin(
            left: Float = 0f,
            top: Float = 0f,
            right: Float = 0f,
            bottom: Float = 0f
    ): VIEW

    /**
     * Adds a background to the given item.
     */
    fun VIEW.background(
            color: ObservableProperty<Color>
    ): VIEW

    fun VIEW.background(
            color: Color
    ): VIEW = background(ConstantObservableProperty(color))

    /**
     * Changes the alpha of a view.
     */
    fun VIEW.alpha(
            alpha: ObservableProperty<Float>
    ): VIEW

    fun VIEW.alpha(
            alpha: Float
    ): VIEW = alpha(ConstantObservableProperty(alpha))

    /**
     * Makes a normally non-clickable element clickable.
     */
    fun VIEW.clickable(
            onClick: () -> Unit
    ): VIEW

    /**
     * Forces the view to be of a certain size
     */
    fun VIEW.setWidth(width: Float): VIEW

    /**
     * Forces the view to be of a certain size
     */
    fun VIEW.setHeight(height: Float): VIEW

    fun VIEW.margin(
            horizontal: Float = 0f,
            top: Float = 0f,
            bottom: Float = 0f
    ) = this.margin(horizontal, top, horizontal, bottom)

    fun VIEW.margin(
            horizontal: Float = 0f,
            vertical: Float = 0f
    ) = this.margin(horizontal, vertical, horizontal, vertical)

    fun VIEW.margin(amount: Float) = this.margin(amount, amount, amount, amount)
}