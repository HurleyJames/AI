package ac.hurley.ai.activity

import ac.hurley.ai.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_activity.*

/**
 * <pre>
 *      @author hurley
 *      date    : 01/05/2020 12:13
 *      github  : https://github.com/HurleyJames
 *      desc    : Activity part
 * </pre>
 */
class ActivityActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)

        btn_normal_lifecycle.setOnClickListener {
            startActivity(Intent(this, NormalLifeCycleActivity::class.java))
        }
    }
}