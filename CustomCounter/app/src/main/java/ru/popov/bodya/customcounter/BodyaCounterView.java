package ru.popov.bodya.customcounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * @author popovbodya
 */

public class BodyaCounterView extends View implements BodyaCounter {

    private int mCount;
    private String mDisplayedCount;
    private RectF mBackgroundRect;
    private int mCornerRadius;

    private Paint mBackgroundPaint;
    private Paint mLinePaint;
    private Paint mNumberPaint;

    public BodyaCounterView(Context context) {
        this(context, null);
    }

    public BodyaCounterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

        // Set up points for canvas drawing.
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        mLinePaint.setStrokeWidth(25f);

        mNumberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setColor(ContextCompat.getColor(context, android.R.color.white));
        mNumberPaint.setTextSize(Math.round(64f * getResources().getDisplayMetrics().scaledDensity));

        mBackgroundRect = new RectF();
        mCornerRadius = Math.round(2f * getResources().getDisplayMetrics().density);

        // Do initial mCount setup.
        setCount(0);
    }

    public BodyaCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BodyaCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("bodya", "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("bodya", "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.e("bodya", "onDraw");

        final int canvasWidth = canvas.getWidth();
        final int canvasHeight = canvas.getHeight();

        final float centerX = canvasWidth * 0.5f;

        mBackgroundRect.set(0f, 0f, canvasWidth, canvasHeight);
        canvas.drawRoundRect(mBackgroundRect, mCornerRadius, mCornerRadius, mBackgroundPaint);

        final float textWidth = mNumberPaint.measureText(mDisplayedCount);
        final float textX = Math.round(centerX - textWidth * 0.5f);
        final float baselineY = Math.round(canvasHeight * 0.6f);

        canvas.drawLine(textX, baselineY, textX + textWidth, baselineY, mLinePaint);
        canvas.drawText(mDisplayedCount, textX, baselineY, mNumberPaint);
    }

    @Override
    public void reset() {
        setCount(0);
    }

    @Override
    public void increment() {
        setCount(mCount + 1);
    }

    @Override
    public int getCount() {
       return mCount;
    }

    @Override
    public void setCount(int count) {
        this.mCount = count;
        this.mDisplayedCount = String.format(Locale.getDefault(), "%04d", count);
        invalidate();
    }
}
