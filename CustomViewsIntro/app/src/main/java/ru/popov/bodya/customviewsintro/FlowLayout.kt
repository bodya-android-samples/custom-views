package ru.popov.bodya.customviewsintro

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup


class FlowLayout : ViewGroup {

    private var gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
    private val widthList = arrayListOf<Int>()

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val parentWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val parentWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val parentHeightSize = MeasureSpec.getSize(heightMeasureSpec)

        var viewGroupWidth = 0
        var viewGroupHeight = 0
        var lineWidth = 0

        for (i in 0 until childCount) {

            val childView: View = getChildAt(i)

            if (childView.visibility == View.GONE) continue

            val childLayoutParams: LayoutParams = childView.layoutParams

            // width
            var childWidthMode = MeasureSpec.EXACTLY
            var childWidthSize = childLayoutParams.width

            when (childLayoutParams.width) {
                LayoutParams.MATCH_PARENT -> {
                    childWidthMode = MeasureSpec.EXACTLY
                    childWidthSize = parentWidthSize - paddingLeft - paddingRight
                }
                LayoutParams.WRAP_CONTENT -> {
                    childWidthMode = MeasureSpec.AT_MOST
                    childWidthSize = parentWidthSize - paddingLeft - paddingRight
                }
            }

            // height
            var childHeightMode = MeasureSpec.UNSPECIFIED
            var childHeightSize = 0

            when {
                childLayoutParams.height == LayoutParams.WRAP_CONTENT -> {
                    childHeightMode = MeasureSpec.AT_MOST
                    childHeightSize = parentHeightSize - paddingTop - paddingBottom
                }
                childLayoutParams.height >= 0 -> {
                    childHeightMode = MeasureSpec.EXACTLY;
                    childHeightSize = childLayoutParams.height;
                }
            }

            childView.measure(
                    MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                    MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
            )


            val childWidthReal = childView.measuredWidth
            val childHeightReal = childView.measuredHeight

            if (lineWidth + childWidthReal > parentWidthSize) {
                viewGroupWidth = Math.max(lineWidth, childWidthReal)
                lineWidth = childWidthReal
                viewGroupHeight += childHeightReal
            } else {
                lineWidth += childWidthReal
                viewGroupHeight = Math.max(viewGroupHeight, childHeightReal)
            }
        }

        viewGroupWidth = Math.max(lineWidth, viewGroupWidth)
        viewGroupWidth = if (parentWidthMode == MeasureSpec.EXACTLY) parentWidthSize else viewGroupWidth
        viewGroupHeight = if (parentHeightMode == MeasureSpec.EXACTLY) parentHeightSize else viewGroupHeight
        setMeasuredDimension(viewGroupWidth, viewGroupHeight)
    }

    override fun onLayout(changed: Boolean, ll: Int, tt: Int, r: Int, b: Int) {
        var height = 0
        var lineWidth = 0
        val childCount = childCount
        widthList.clear()
        val widthSize = measuredWidth
        for (i in 0 until childCount) {

            val childView: View = getChildAt(i)

            if (childView.visibility == View.GONE) continue

            val childLayoutParams: LayoutParams = childView.layoutParams
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight
            if (lineWidth + childWidth > widthSize) {
                widthList.add(lineWidth)
                lineWidth = childWidth
                height += childHeight
            } else {
                lineWidth += childWidth
                height = Math.max(height, childHeight)
            }
        }
        widthList.add(lineWidth)

        val verticalGravityMargin: Int = when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.BOTTOM -> getHeight() - height
            Gravity.CENTER_VERTICAL -> (getHeight() - height) / 2
            else -> {
                0
            }
        }

        val globalWidth = measuredWidth
        lineWidth = 0
        var top = verticalGravityMargin
        var left = 0
        var currentLineWidth: Int
        var lineCnt = 0
        var t: Int
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            if (v.visibility == View.GONE)
                continue
            val lp = v.layoutParams as ViewGroup.LayoutParams
            val childWidth = v.measuredWidth
            val childHeight = v.measuredHeight

            var l = left

            if (i == 0 || lineWidth + childWidth > widthSize) {  // Новая линия
                lineWidth = childWidth
                currentLineWidth = widthList[lineCnt]
                lineCnt++
                val lineHorizontalGravityMargin: Int = when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.CENTER_HORIZONTAL -> (globalWidth - currentLineWidth) / 2
                    Gravity.END -> globalWidth - currentLineWidth
                    else -> 0
                }
                left = lineHorizontalGravityMargin
                l = left
                left += childWidth
                top += childHeight
            } else {
                left += childWidth
                lineWidth += childWidth
            }
            t = top - childHeight
            v.layout(l, t, l + childWidth, t + childHeight)
        }
    }

    fun setGravity(gravity: Int) {
        if (this.gravity != gravity) {
            this.gravity = gravity
            requestLayout()
        }
    }


}