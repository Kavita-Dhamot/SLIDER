package kavs.myappcompany.slider

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.slide_1.*
import kotlinx.android.synthetic.main.slide_2.*
import kotlinx.android.synthetic.main.slide_3.*


class MainActivity : AppCompatActivity() {

    private lateinit var  myAdapter: MyAdapter
    private lateinit var dotsTv: Array<TextView?>
    private lateinit var layouts : IntArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if(!isFirstTimeAppStart()) {
            setAppStartStatus(false)
            startActivity(Intent(this, NextActivity::class.java))
            finish()

        }


        setContentView(R.layout.activity_main)


        lottie2.setAnimation("bmi.json")
        lottie2.playAnimation()
        lottie2.loop(true)

        lottie3.setAnimation("worry.json")
        lottie3.playAnimation()
        lottie3.loop(true)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.hide()

        statusBarTransparent()

        btn_next.setOnClickListener {
            val currentPage: Int = viewPager.currentItem + 1

            if(currentPage < layouts.size) {
                viewPager.currentItem = currentPage
            }
            else {
                setAppStartStatus(false)
                startActivity(Intent(this, NextActivity::class.java))
                finish()
            }

        }

        btn_skip.setOnClickListener {
            setAppStartStatus(false)
            startActivity(Intent(this, NextActivity::class.java))
            finish()

        }

        layouts = intArrayOf(R.layout.slide_1, R.layout.slide_2, R.layout.slide_3)
        myAdapter = MyAdapter(layouts, applicationContext)
        viewPager.adapter = myAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == layouts.size - 1) {
                    btn_next.text = "START"
                    btn_skip.visibility = View.GONE
                } else {
                    btn_next.text = "NEXT"
                    btn_skip.visibility = View.VISIBLE

                }
                setDots(position)

            }

        })
        setDots(0)

    }

    private fun isFirstTimeAppStart() : Boolean {
        val pref = applicationContext.getSharedPreferences("SLIDER_APP", Context.MODE_PRIVATE)
        return pref.getBoolean("APP_START", true)
    }

    private fun setAppStartStatus(status: Boolean) {
        val pref = applicationContext.getSharedPreferences("SLIDER_APP", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putBoolean("APP_START", status)
        editor.apply()
    }

    private fun statusBarTransparent() {
        if(Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setDots(page: Int) {
        dotsLayout.removeAllViews()
        dotsTv = arrayOfNulls(layouts.size)

        for(i in dotsTv.indices) {
            dotsTv[i] = TextView(this)
            dotsTv[i]!!.text = Html.fromHtml("&#8226;")
            dotsTv[i]!!.textSize = 30f
            dotsTv[i]!!.setTextColor(Color.parseColor("#a9b4bb"))
            dotsLayout.addView(dotsTv[i])
        }

        if(dotsTv.isNotEmpty()) {
            dotsTv[page]!!.setTextColor(Color.parseColor("#ffffff"))
        }
    }
}