package com.lightningkite.kotlinx.ui.android

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.webkit.WebView
import android.widget.*
import com.lightningkite.kotlinx.locale.*
import com.lightningkite.kotlinx.locale.Date
import com.lightningkite.kotlinx.observable.list.ObservableList
import com.lightningkite.kotlinx.observable.list.ObservableListListenerSet
import com.lightningkite.kotlinx.observable.list.lifecycle.bind
import com.lightningkite.kotlinx.observable.property.*
import com.lightningkite.kotlinx.observable.property.lifecycle.bind
import com.lightningkite.kotlinx.observable.property.lifecycle.listen
import com.lightningkite.kotlinx.ui.*
import com.lightningkite.kotlinx.ui.color.Color
import com.lightningkite.kotlinx.ui.helper.TreeObservableProperty
import com.lightningkite.kotlinx.ui.helper.defaultEntryContext
import com.lightningkite.kotlinx.ui.helper.defaultSmallWindow
import java.text.DecimalFormat
import java.text.ParseException
import java.util.*

data class DesiredMargins(val left: Int, val top: Int, val right: Int, val bottom: Int)

private val desiredMargins = WeakHashMap<View, DesiredMargins>()

class AndroidMaterialViewFactory(
        val context: Context,
        override val theme: Theme,
        override val colorSet: ColorSet = theme.main
) : ViewFactory<View> {

    val dip = context.resources.displayMetrics.density

    override fun withColorSet(colorSet: ColorSet): ViewFactory<View> = AndroidMaterialViewFactory(context = context, theme = theme, colorSet = colorSet)


    override fun window(
            stack: StackObservableProperty<ViewGenerator<View>>,
            tabs: List<Pair<TabItem, ViewGenerator<View>>>,
            actions: ObservableList<Pair<TabItem, () -> Unit>>
    ): View = defaultSmallWindow(stack, tabs, actions)

    override fun pages(page: MutableObservableProperty<Int>, vararg pageGenerator: ViewGenerator<View>): View = frame {
        PlacementPair.fillFill + ViewPager(context).apply {
            adapter = object : PagerAdapter() {
                override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

                override fun getCount(): Int = pageGenerator.size

                override fun instantiateItem(container: ViewGroup, position: Int): Any {
                    val view = pageGenerator[position].generate()
                    view.lifecycle.parent = this@apply.lifecycle
                    container.addView(view)
                    return view
                }
            }
        }
        PlacementPair.bottomCenter + text(text = page.transform { "${it + 1} / ${pageGenerator.size}" }, size = TextSize.Tiny)
    }

    override fun tabs(options: ObservableList<TabItem>, selected: MutableObservableProperty<TabItem>): View = TabLayout(context).apply {
        lifecycle.bind(options.onUpdate) {
            removeAllTabs()
            for (item in it) {
                addTab(newTab().apply {
                    this.text = item.text
                    this.icon = item.image.android()
                    this.tag = item
                    if (selected.value == item) {
                        this.select()
                    }
                })
            }
        }
        addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                (tab?.tag as? TabItem)?.let { selected.value = it }
            }
        })
    }

    class ListViewHolder<T>(
            context: Context,
            val makeView: (obs: ObservableProperty<T>) -> View,
            val parent: TreeObservableProperty
    ) : RecyclerView.ViewHolder(FrameLayout(context).apply {
        layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }) {
        var property: StandardObservableProperty<T>? = null
        fun update(item: T) {
            if (property == null) {
                property = StandardObservableProperty(item)
                val newView = makeView.invoke(property!!)
                newView.lifecycle.parent = parent
                (itemView as FrameLayout).addView(newView, FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT))
            } else {
                property!!.value = item
            }
        }
    }

    override fun <T> list(
            data: ObservableList<T>,
            onBottom: () -> Unit,
            makeView: (obs: ObservableProperty<T>) -> View
    ): View = RecyclerView(context).apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = object : RecyclerView.Adapter<ListViewHolder<T>>() {
            override fun getItemCount(): Int = data.size

            override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
            ): ListViewHolder<T> = ListViewHolder(context, makeView, this@apply.lifecycle)

            override fun onBindViewHolder(holder: ListViewHolder<T>, position: Int) {
                holder.update(data[position])
            }
        }
        lifecycle.bind(data, ObservableListListenerSet<T>(
                onAddListener = { item, position -> adapter.notifyItemInserted(position) },
                onRemoveListener = { item, position -> adapter.notifyItemRemoved(position) },
                onChangeListener = { oldItem, newItem, position -> adapter.notifyItemChanged(position) },
                onMoveListener = { item, oldPosition, newPosition -> adapter.notifyItemMoved(oldPosition, newPosition) },
                onReplaceListener = { list -> adapter.notifyDataSetChanged() }
        ))
    }

    override fun text(
            text: ObservableProperty<String>,
            style: TextStyle,
            size: TextSize,
            align: AlignPair
    ): View = TextView(context).apply {
        textSize = size.sp()
        lifecycle.bind(text) {
            this.text = it
        }
        setTextColor(when (style) {
            TextStyle.Normal -> colorSet.foreground.toInt()
            TextStyle.Danger -> Color.red.toInt()
            TextStyle.Faded -> colorSet.foregroundDisabled.toInt()
        })
        gravity = align.android()
    }

    override fun image(
            minSize: Point,
            scaleType: ImageScaleType,
            image: ObservableProperty<Image>
    ): View = ImageView(context).apply {
        minimumWidth = (minSize.x * dip).toInt()
        minimumHeight = (minSize.y * dip).toInt()
        this.scaleType = when (scaleType) {
            ImageScaleType.Crop -> ImageView.ScaleType.CENTER_CROP
            ImageScaleType.Fill -> ImageView.ScaleType.CENTER_INSIDE
            ImageScaleType.Center -> ImageView.ScaleType.CENTER
        }
        lifecycle.bind(image) {
            setImageDrawable(it.android())
        }
    }

    override fun web(content: ObservableProperty<String>): View = WebView(context).apply {
        lifecycle.bind(content) {
            if (it.startsWith("http"))
                loadUrl(it)
            else
                loadData(it, "text/html", Charsets.UTF_8.toString())
        }
    }

    override fun button(
            label: ObservableProperty<String>,
            image: ObservableProperty<Image?>,
            onClick: () -> Unit
    ): View = Button(context).apply {
        background.setColorFilter(theme.accent.background.toInt(), PorterDuff.Mode.MULTIPLY)
        setTextColor(theme.accent.foreground.toInt())
        lifecycle.bind(label) {
            this.text = it
        }
        lifecycle.bind(image) {
            setCompoundDrawablesWithIntrinsicBounds(it?.android(), null, null, null)
        }
        setOnClickListener { onClick.invoke() }
    }

    override fun imageButton(
            image: ObservableProperty<Image>,
            label: ObservableProperty<String?>,
            onClick: () -> Unit
    ): View = ImageButton(context).apply {
        this.setBackgroundResource(selectableItemBackgroundBorderlessResource)
        lifecycle.bind(label) {
            if (Build.VERSION.SDK_INT > 26) {
                this.tooltipText = it
            }
        }
        lifecycle.bind(image) {
            setImageDrawable(it.android())
        }
        setOnClickListener { onClick.invoke() }
    }

    override fun entryContext(
            label: String,
            help: String?,
            icon: Image?,
            feedback: ObservableProperty<Pair<TextStyle, String>?>,
            field: View
    ): View = defaultEntryContext(label, help, icon, feedback, field)


    open class StandardListAdapter<T>(
            val context: Context,
            list: List<T>,
            val parent: TreeObservableProperty,
            val makeView: (obs: ObservableProperty<T>) -> View
    ) : BaseAdapter() {

        class ItemObservable<T>(init: T) : StandardObservableProperty<T>(init) {
            var index: Int = 0
            override fun remove(element: (T) -> Unit): Boolean {
                return super.remove(element)
            }
        }

        var list: List<T> = list
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int = list.size
        override fun getItem(position: Int): Any? = list[position]
        override fun getItemId(position: Int): Long = position.toLong()

        @Suppress("UNCHECKED_CAST")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val item = list[position]
            if (convertView == null) {
                val newObs = ItemObservable(list[position])
                val newView = makeView(newObs)
                newView.lifecycle.parent = this.parent
                newView.tag = newObs
                newObs.index = position
                newView.layoutParams = AbsListView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                return newView
            } else {
                val obs = convertView.tag as ItemObservable<T>
                obs.index = position
                obs.value = (list[position])
                return convertView
            }
        }
    }

    override fun <T> picker(
            options: ObservableList<T>,
            selected: MutableObservableProperty<T>,
            makeView: (obs: ObservableProperty<T>) -> View
    ): View = Spinner(context).apply {
        val newAdapter: StandardListAdapter<T> = StandardListAdapter<T>(context, options, lifecycle, makeView)
        adapter = newAdapter

        var indexAlreadySet = false

        lifecycle.listen(options.onUpdate) {
            newAdapter.notifyDataSetChanged()
            val index = options.indexOf(selected.value)
            println("update to $index - ${selected.value}")
            if (index == -1) {
                println("could not find ${selected.value}")
                setSelection(0)
                return@listen
            }
            setSelection(index)
        }

        lifecycle.bind(selected) { it ->
            val index = options.indexOf(it)
            println("selected to $index - $it")
            if (index == -1) {
                println("could not find ${it?.hashCode()} in ${options.joinToString { it?.hashCode().toString() }}")
                setSelection(0)
                return@bind
            }
            if (!indexAlreadySet) {
                setSelection(index)
            } else {
                indexAlreadySet = false
            }
        }

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("set to $position - ${options[position]}")
                indexAlreadySet = true
                selected.value = (options[position])
            }
        }
    }

    override fun textField(
            text: MutableObservableProperty<String>,
            placeholder: String,
            type: TextInputType
    ) = EditText(context).apply {
        inputType = type.android()
        hint = placeholder
        setText(text.value)
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (text.value != s) {
                    text.value = (s.toString())
                }
            }
        })
        lifecycle.listen(text) {
            if (it != this@apply.text.toString()) {
                this.setText(it)
            }
        }

        setTextColor(colorSet.foreground.toInt())
        setHintTextColor(colorSet.foregroundDisabled.toInt())
    }

    override fun textArea(
            text: MutableObservableProperty<String>,
            placeholder: String,
            type: TextInputType
    ): View = textField(text, placeholder, type).apply {
        maxLines = Int.MAX_VALUE
        minHeight = (100 * dip).toInt()
    }

    override fun numberField(
            value: MutableObservableProperty<Number?>,
            placeholder: String,
            type: NumberInputType,
            decimalPlaces: Int
    ): View = EditText(context).apply {
        inputType = type.android()
        hint = placeholder

        val format = if (decimalPlaces == 0) DecimalFormat("#") else DecimalFormat("#." + "#".repeat(decimalPlaces))

        var lastValue: Double? = null
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lastValue = null

                setTextColor(colorSet.foreground.toInt())
                if (!s.isNullOrBlank()) {
                    try {
                        lastValue = format.parse(s.toString()).toDouble()
                    } catch (e: ParseException) {
                        try {
                            lastValue = s.toString().toDouble()
                        } catch (e: NumberFormatException) {
                            //Not a number?
                            setTextColor(Color.red.toInt())
                        }
                    }
                }

                if (value.value != lastValue) {
                    value.value = (lastValue)
                }
            }
        })

        lifecycle.bind(value) {
            if (it != lastValue) {
                if (it == null) this.setText("")
                else this.setText(format.format(it))
            }
        }

        setHintTextColor(colorSet.foregroundDisabled.toInt())
    }

    override fun datePicker(observable: MutableObservableProperty<Date>): View = button(
            label = observable.transform { Locales.defaultLocale.renderDate(it) },
            onClick = {
                val start: Calendar = observable.value.toJava()
                DatePickerDialog(
                        context,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            start.set(Calendar.YEAR, year)
                            start.set(Calendar.MONTH, monthOfYear)
                            start.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            observable.value = start.toDate()
                        },
                        start.get(Calendar.YEAR),
                        start.get(Calendar.MONTH),
                        start.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
    )

    override fun dateTimePicker(observable: MutableObservableProperty<DateTime>): View = button(
            label = observable.transform { Locales.defaultLocale.renderDateTime(it) },
            onClick = {
                val start: Calendar = observable.value.toJava()
                DatePickerDialog(
                        context,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            start.set(Calendar.YEAR, year)
                            start.set(Calendar.MONTH, monthOfYear)
                            start.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            TimePickerDialog(
                                    context,
                                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                        start.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                        start.set(Calendar.MINUTE, minute)
                                        observable.value = start.toDateTime()
                                    },
                                    start.get(Calendar.HOUR_OF_DAY),
                                    start.get(Calendar.MINUTE),
                                    false
                            ).show()
                        },
                        start.get(Calendar.YEAR),
                        start.get(Calendar.MONTH),
                        start.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
    )

    override fun timePicker(observable: MutableObservableProperty<Time>): View = button(
            label = observable.transform { Locales.defaultLocale.renderTime(it) },
            onClick = {
                val start: Calendar = observable.value.toJava()
                TimePickerDialog(
                        context,
                        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            start.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            start.set(Calendar.MINUTE, minute)
                            observable.value = start.toTime()
                        },
                        start.get(Calendar.HOUR_OF_DAY),
                        start.get(Calendar.MINUTE),
                        false
                ).show()
            }
    )

    override fun work(view: View, isWorking: ObservableProperty<Boolean>): View {
        val bar = ProgressBar(context).apply {
            isIndeterminate = true
        }
        return swap(
                view = isWorking.transform {
                    val nextView = if (it) bar else view
                    nextView to Animation.Fade
                }
        )
    }

    override fun progress(view: View, progress: ObservableProperty<Float>): View {
        val bar = ProgressBar(context).apply {
            isIndeterminate = false
            max = 100
            lifecycle.bind(progress) {
                this.progress = (it * 100).toInt()
            }
        }
        return swap(
                view = progress.transform {
                    val nextView = if (it == 1f) view else bar
                    nextView to Animation.Fade
                }
        )
    }

    override fun slider(range: IntRange, observable: MutableObservableProperty<Int>): View = SeekBar(context).apply {
        max = range.endInclusive - range.start + 1
        lifecycle.bind(observable) {
            val newProg = it - range.start
            if (this.progress != newProg) {
                this.progress = newProg
            }
        }
        setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val newValue = progress + range.start
                    if (observable.value != newValue) {
                        observable.value = newValue
                    }
                }
            }
        })
    }

    override fun toggle(observable: MutableObservableProperty<Boolean>): View = CheckBox(context).apply {
        this.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked != observable.value) {
                observable.value = (isChecked)
            }
        }
        lifecycle.bind(observable) {
            val value = observable.value
            if (isChecked != value) {
                isChecked = value
            }
        }
    }

    override fun refresh(contains: View, working: ObservableProperty<Boolean>, onRefresh: () -> Unit): View = SwipeRefreshLayout(context).apply {
        addView(contains, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
        contains.lifecycle.parent = this.lifecycle
    }

    override fun scroll(view: View): View = ScrollView(context).apply {
        isFillViewport = true
        view.lifecycle.parent = this.lifecycle
        addView(view, ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
    }

    override fun swap(view: ObservableProperty<Pair<View, Animation>>): View = FrameLayout(context).apply {
        val man = ViewSwapManager<FrameLayout>(this, { FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT) })
        var currentView: View? = null
        lifecycle.bind(view) {
            currentView?.lifecycle?.parent = null
            man.swap(it.first, it.second.android())
            it.first.lifecycle.parent = this.lifecycle
            currentView = it.first
        }
    }

    override fun space(size: Point): View = Space(context).apply {
        minimumWidth = (size.x * dip).toInt()
        minimumHeight = (size.y * dip).toInt()
    }

    override fun horizontal(vararg views: Pair<PlacementPair, View>): View = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        for ((placement, subview) in views) {
            subview.lifecycle.parent = this.lifecycle
            layoutParams = LinearLayout.LayoutParams(
                    placement.horizontal.androidSize(dip),
                    placement.vertical.androidSize(dip),
                    if (placement.horizontal.align == Align.Fill) placement.horizontal.size else 0f
            ).apply {
                gravity = placement.alignPair.android()
                desiredMargins[subview]?.let {
                    setMargins(it.left, it.top, it.right, it.bottom)
                }
            }
            addView(
                    subview,
                    layoutParams
            )
        }
    }

    override fun vertical(vararg views: Pair<PlacementPair, View>): View = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        for ((placement, subview) in views) {
            subview.lifecycle.parent = this.lifecycle
            layoutParams = LinearLayout.LayoutParams(
                    placement.horizontal.androidSize(dip),
                    placement.vertical.androidSize(dip),
                    if (placement.vertical.align == Align.Fill) placement.vertical.size else 0f
            ).apply {
                gravity = placement.alignPair.android()
                desiredMargins[subview]?.let {
                    setMargins(it.left, it.top, it.right, it.bottom)
                }
            }
            addView(
                    subview,
                    layoutParams
            )
        }
    }

    override fun frame(vararg views: Pair<PlacementPair, View>): View = FrameLayout(context).apply {
        for ((placement, subview) in views) {
            subview.lifecycle.parent = this.lifecycle
            layoutParams = FrameLayout.LayoutParams(
                    placement.horizontal.androidSize(dip),
                    placement.vertical.androidSize(dip),
                    placement.alignPair.android()
            ).apply {
                desiredMargins[subview]?.let {
                    setMargins(it.left, it.top, it.right, it.bottom)
                }
            }
            addView(
                    subview,
                    layoutParams
            )
        }
    }


    override fun View.margin(left: Float, top: Float, right: Float, bottom: Float): View {
        desiredMargins[this] = DesiredMargins(
                (left * dip).toInt(),
                (top * dip).toInt(),
                (right * dip).toInt(),
                (bottom * dip).toInt()
        )
        return this
    }

    override fun View.background(color: ObservableProperty<Color>): View = this.apply {
        lifecycle.bind(color) {
            setBackgroundColor(it.toInt())
        }
    }

    override fun card(view: View): View = CardView(context).apply {
        setCardBackgroundColor(colorSet.background.toInt())
        view.lifecycle.parent = this.lifecycle
        addView(view)
    }

    override fun View.alpha(alpha: ObservableProperty<Float>): View = this.apply {
        lifecycle.bind(alpha) {
            this.alpha = it
        }
    }

    override fun View.clickable(onClick: () -> Unit): View = this.apply {
        setOnClickListener { onClick.invoke() }
    }


}