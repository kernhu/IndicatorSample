package org.ardor.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: Kern Hu
 * @emali: create at: 2019/6/26 10:23.
 * modify at: 2019/6/26 10:23.
 * develop version name :
 * modify version name :
 * description: This's ... a view looks like a progress bar
 */
public class LinearIndicator extends View {

    private int mHeight;
    private int mBackgroundColor;
    private int mProgressColorStart;
    private int mProgressColorMiddle;
    private int mProgressColorEnd;
    private float mCurrentProgress;
    private float mMaxProgress;
    private boolean mAuto;
    private Paint mProgressPaint;
    private float mRate;

    public LinearIndicator(Context context) {
        this(context, null);
    }

    public LinearIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDefaultStyleAttr(attrs, defStyleAttr);
        initPaint();
    }

    private void initDefaultStyleAttr(AttributeSet attrs, int defStyleAttr) {

        final TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.LinearIndicator, defStyleAttr, 0);
        mHeight = (int) attributes.getLayoutDimension(R.styleable.LinearIndicator_android_layout_height, 2);
        mBackgroundColor = attributes.getColor(R.styleable.LinearIndicator_indicator_background_color, 0x50FFFFFF);
        mProgressColorStart = attributes.getColor(R.styleable.LinearIndicator_indicator_progress_color_start, 0xFFFFFFFF);
        mProgressColorMiddle = attributes.getColor(R.styleable.LinearIndicator_indicator_progress_color_middle, 0xFFFFFFFF);
        mProgressColorEnd = attributes.getColor(R.styleable.LinearIndicator_indicator_progress_color_end, 0xFFFFFFFF);
        mCurrentProgress = attributes.getFloat(R.styleable.LinearIndicator_indicator_current_progress, 0F);
        mMaxProgress = attributes.getFloat(R.styleable.LinearIndicator_indicator_max_progress, 100F);
        mAuto = attributes.getBoolean(R.styleable.LinearIndicator_indicator_auto, false);
        attributes.recycle();

    }

    private void initPaint() {

        /******/
        mProgressPaint = new Paint();
        int[] colors = {mProgressColorStart, mProgressColorMiddle, mProgressColorEnd};
        float[] position = {0f, 0.5f, 1.0f};
        LinearGradient linearGradient = new LinearGradient(0, 0, ViewUtils.getScreenWidth(getContext()), 0, colors, position, Shader.TileMode.CLAMP);
        mProgressPaint.setShader(linearGradient);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(getMeasuredWidth());
        mProgressPaint.setStyle(Paint.Style.FILL);

        /*******/
        mRate = ViewUtils.getScreenWidth(getContext()) / mMaxProgress;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(mBackgroundColor);
        RectF rectF = new RectF(0,
                0,
                mRate * mCurrentProgress, mHeight);
        canvas.drawRect(rectF, mProgressPaint);

    }

    /*****************************************************************************************/
    public void setHeight(int height) {
        mHeight = height;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public int getProgressColorStart() {
        return mProgressColorStart;
    }

    public void setProgressColorStart(int progressColorStart) {
        mProgressColorStart = progressColorStart;
    }

    public int getProgressColorEnd() {
        return mProgressColorEnd;
    }

    public void setProgressColorEnd(int progressColorEnd) {
        mProgressColorEnd = progressColorEnd;
    }

    public int getMaxProgress() {
        return (int) mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    public boolean isAuto() {
        return mAuto;
    }

    public void setAuto(boolean auto) {
        mAuto = auto;
    }

    public void setCurrentProgress(float currentProgress) {
        if (currentProgress > mMaxProgress) {
            throw new RuntimeException("progress mast less than  max progress");
        }
        setProgress(currentProgress);
    }
    /*****************************************************************************************/
    /**
     * @param currentProgress
     */
    private void setProgress(float currentProgress) {

        synchronized (this) {
            for (int i = (int) mCurrentProgress; i < currentProgress; i++) {
                mCurrentProgress++;
                if (getVisibility() != View.VISIBLE) {
                    setVisibility(View.VISIBLE);
                }
                postInvalidate();
            }
        }
        if (mAuto) {
            if (mCurrentProgress == mMaxProgress && getVisibility() == View.VISIBLE) {
                setVisibility(View.GONE);
                mCurrentProgress = 0;
            }
        } else {
            if (mCurrentProgress == mMaxProgress) {
                mCurrentProgress = 0;
            }
        }
    }

}
