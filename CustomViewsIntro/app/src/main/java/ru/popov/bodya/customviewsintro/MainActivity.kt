package ru.popov.bodya.customviewsintro

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_flowlayout.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flowlayout)
        initViews()
    }

    private fun initViews() {
        val fruits = arrayOf("Apple", "Mango", "Peach", "Banana", "Orange", "Grapes", "Watermelon", "Tomato")
        val rnd = Random()
        add_letter.setOnClickListener {
            val tv = TextView(this)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))

            val idx = rnd.nextInt(fruits.size)
            tv.text = fruits[idx]
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            tv.layoutParams = layoutParams
            flow_layout.addView(tv)
        }

        remove_letter.setOnClickListener {
            val count = flow_layout.childCount;
            if (count > 0) {
                flow_layout.removeViewAt(count - 1);
            }
        }
    }
}
