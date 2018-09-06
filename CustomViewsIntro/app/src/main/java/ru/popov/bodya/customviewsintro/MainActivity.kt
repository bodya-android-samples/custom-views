package ru.popov.bodya.customviewsintro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        paddingUpButton.setOnClickListener { customView.customPaddingUp() }
        paddingBottomButton.setOnClickListener { customView.customPaddingDown() }
        swapColorButton.setOnClickListener { customView.swapColor() }
    }
}
