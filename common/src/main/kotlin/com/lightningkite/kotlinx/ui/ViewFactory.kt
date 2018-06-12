package com.lightningkite.kotlinx.ui

import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.locale.DateTime
import com.lightningkite.kotlinx.locale.Time
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.property.ConstantObservableProperty
import com.lightningkite.kotlinx.observable.property.MutableObservableProperty
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StackObservableProperty
import com.lightningkite.kotlinx.ui.color.Color

interface ViewFactory<VIEW> {

    //Theme

    val theme: Theme
    val colorSet: ColorSet

    /**
     * Returns another factory that uses the given color set.
     * Used when changing themes or making a dialog.
     */
    fun withColorSet(colorSet: ColorSet): ViewFactory<VIEW>

    //Navigation

    /**
     * The main window of the program - provides a stack, navigation, tab navigation, and actions.
     */
    fun window(
            stack: StackObservableProperty<ViewGenerator<VIEW>>,
            tabs: List<Pair<TabItem, ViewGenerator<VIEW>>>,
            actions: ObservableList<Pair<TabItem, ()->Unit>>
    ): VIEW

    /**
     * A set of ordered pages you can swap through with the built-in navigator.
     */
    fun pages(
            page: MutableObservableProperty<Int>,
            vararg pageGenerator: ViewGenerator<VIEW>
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
            style: TextStyle = TextStyle.Normal,
            size: TextSize = TextSize.Body,
            align: AlignPair = AlignPair.CenterLeft
    ): VIEW

    /**
     * Shows an image with the given scaling and a minimum size.
     * While loading, it will show a loading indicator.
     */
    fun image(
            minSize: Point,
            scaleType: ImageScaleType,
            image: ObservableProperty<Image>
    ): VIEW

    /**
     * Shows a webpage within this view.
     * If the string starts with 'http', it will be interpreted as a URL.
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
            image: ObservableProperty<Image?> = ConstantObservableProperty(null),
            label: ObservableProperty<String?> = ConstantObservableProperty(null),
            onClick:()->Unit
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
            feedback: ObservableProperty<Pair<TextStyle, String>?> = ConstantObservableProperty(null),
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
            working:ObservableProperty<Boolean>,
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
            vararg views: Pair<PlacementPair, VIEW>
    ): VIEW

    /**
     * Places elements vertically, top to bottom.
     * The placement pairs determine whether or not the elements are stretched or shifted around.
     */
    fun vertical(
            vararg views: Pair<PlacementPair, VIEW>
    ): VIEW

    /**
     * Places elements on top of each other, back to front.
     * The placement pairs determine whether or not the elements are stretched or shifted around.
     */
    fun frame(
            vararg views: Pair<PlacementPair, VIEW>
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

    /**
     * Changes the alpha of a view.
     */
    fun VIEW.alpha(
            alpha: ObservableProperty<Float>
    ): VIEW

    /**
     * Makes a normally non-clickable element clickable.
     */
    fun VIEW.clickable(
            onClick: () -> Unit
    ): VIEW


    //Extensions
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