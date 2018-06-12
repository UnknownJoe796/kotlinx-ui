package desktop.kotlin.lk.test_android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lightningkite.kotlinx.ui.Theme
import com.lightningkite.kotlinx.ui.android.AndroidMaterialViewFactory
import com.lightningkite.kotlinx.ui.android.lifecycle
import com.lightningkite.kotlinx.ui.test.MainVG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = AndroidMaterialViewFactory(this, Theme.dark())
        val main = MainVG(factory)
        val view = main.generate()
        view.lifecycle.alwaysOn = true
        setContentView(view)
    }
}
