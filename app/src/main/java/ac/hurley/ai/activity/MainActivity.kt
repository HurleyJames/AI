package ac.hurley.ai.activity

import ac.hurley.ai.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <pre>
 *      @author hurley
 *      date    : 01/05/2020 11:19
 *      github  : https://github.com/HurleyJames
 *      desc    : HomePage
 * </pre>
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rl_activity.setOnClickListener {
            startActivity(Intent(this, ActivityActivity::class.java))
        }
    }
}