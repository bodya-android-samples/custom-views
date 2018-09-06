package ru.popov.bodya.customviewsintro

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.View


class CustomView : View {

    companion object {
        private const val DEFAULT_PADDING = 30
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect: Rect = Rect()
    private var color: Int = 0
    private var padding = 0

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        rect.left = padding
        rect.right = width - padding
        rect.top = padding
        rect.bottom = height - padding

        Log.e("bodya", "onDraw with width: $width")
        Log.e("bodya", "onDraw with height: $height")

        canvas?.drawRect(rect, paint)
    }

    fun swapColor() {
        paint.color = if (paint.color == color) Color.RED else color
        postInvalidate()
    }

    fun customPaddingUp() {
        padding += DEFAULT_PADDING
        postInvalidate()
    }

    fun customPaddingDown() {
        padding -= DEFAULT_PADDING
        postInvalidate()
    }

    private fun init(set: AttributeSet?) {
        val ta = context.obtainStyledAttributes(set, R.styleable.CustomView)
        color = ta.getColor(R.styleable.CustomView_square_color, Color.GREEN)
        paint.color = color
        ta.recycle()
    }
}
