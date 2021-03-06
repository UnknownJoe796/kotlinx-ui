package desktop.kotlin.lk.test_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.lightningkite.kotlinx.httpclient.HttpClient
import com.lightningkite.kotlinx.ui.android.AndroidMaterialViewFactory
import com.lightningkite.kotlinx.ui.android.access.AccessibleActivity
import com.lightningkite.kotlinx.ui.android.lifecycle
import com.lightningkite.kotlinx.ui.color.Theme
import com.lightningkite.kotlinx.ui.test.MainVG

class MainActivity : AccessibleActivity() {

    companion object {
        val main = MainVG<View>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val handler = Handler(Looper.getMainLooper())
        HttpClient.resultThread = { handler.post(it) }

        val factory = AndroidMaterialViewFactory(this, Theme.light())
        val view = main.generate(factory)
        view.lifecycle.alwaysOn = true
        setContentView(view)
    }


}
