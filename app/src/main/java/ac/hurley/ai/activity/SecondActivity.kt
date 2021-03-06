package ac.hurley.ai.activity

import ac.hurley.ai.R
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_second_activity.*

/**
 * <pre>
 *      @author hurley
 *      date    : 01/05/2020 13:48
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
class SecondActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.i("onCreate")
        setContentView(R.layout.activity_second_activity)

        btn_first_activity.setOnClickListener {
            startActivity(Intent(this, NormalLifeCycleActivity::class.java))
        }
    }

    override fun onContentChanged() {
        super.onContentChanged()
        LogUtils.i("onContentChanged")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtils.i("onNewIntent")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.i("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.i("onRestart")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        LogUtils.i("onPostCreate")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.i("onResume")
    }

    override fun onPostResume() {
        super.onPostResume()
        LogUtils.i("onPostResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.i("onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i("onDestroy")
    }

    override fun finish() {
        LogUtils.i("finish")
        super.finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LogUtils.i("onConfigurationChanged")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        LogUtils.i("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtils.i("onRestoreInstanceState")
    }
}