package com.lightningkite.kotlinx.ui

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import com.lightningkite.kotlinx.lambda.invokeAll
import com.lightningkite.kotlinx.observable.property.ObservableProperty
import com.lightningkite.kotlinx.observable.property.StandardObservableProperty
import com.lightningkite.kotlinx.ui.geometry.Point
import lk.android.activity.access.ActivityAccess

actual object ApplicationAccess {

    private var access: ActivityAccess? = null

    init {
        ValueAnimator().apply {
            setIntValues(0, 100)
            duration = 10000L
            repeatCount = ValueAnimator.INFINITE
            repeatCount = ValueAnimator.RESTART
            addUpdateListener { onAnimationFrame.invokeAll() }
            start()
        }
    }

    fun init(access: ActivityAccess) {
        val oldAccess = this.access
        oldAccess?.onResume?.remove(resumeListener)
        oldAccess?.onPause?.remove(pauseListener)
        oldAccess?.onBackPressed?.remove(backListener)

        this.access = access
        privateDisplaySize.value = access.context.resources.displayMetrics.let {
            Point(
                    it.widthPixels / it.density,
                    it.heightPixels / it.density
            )
        }
        privateIsInForeground.value = true
        access.onPause.add(pauseListener)
        access.onResume.add(resumeListener)
        access.onBackPressed.add(backListener)
    }

    val pauseListener = { ->
        privateIsInForeground.value = false
    }
    val resumeListener = { ->
        privateIsInForeground.value = true
    }
    val backListener = { ->
        onBackPressed.reversed().any { it.invoke() }
    }

    private val privateDisplaySize = StandardObservableProperty(Point())
    actual val displaySize: ObservableProperty<Point> //can change on rotation, etc.
        get() = privateDisplaySize

    private val privateIsInForeground = StandardObservableProperty(false)
    actual val isInForeground: ObservableProperty<Boolean> get() = privateIsInForeground

    actual val onBackPressed: MutableList<() -> Boolean> = ArrayList()

    actual val onAnimationFrame: MutableCollection<() -> Unit> = ArrayList()


    val handler = Handler(Looper.getMainLooper())
    actual fun runLater(action: () -> Unit) {
        handler.post(action)
    }

    actual fun runAfterDelay(delayMilliseconds: Long, action: () -> Unit) {
        handler.postDelayed(action, delayMilliseconds)
    }

}