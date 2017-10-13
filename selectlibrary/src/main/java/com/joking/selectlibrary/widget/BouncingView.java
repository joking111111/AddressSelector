package com.joking.selectlibrary.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.joking.selectlibrary.R;


public class BouncingView extends View {
    private Paint mPaint;
    private int mArcHeight;//弧线的高度
    private int mMaxArcHeight;//弧线的最高高度
    private Status mStatus = Status.NONE;
    private Path mPath = new Path();
    private AnimationListener animationListener;

    private enum Status {
        NONE,
        STATUS_SMOOTH_UP,
        STATUS_DOWN,
    }

    public BouncingView(Context context) {
        this(context, null, -1);
    }

    public BouncingView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BouncingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mMaxArcHeight = 100;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BouncingView);

            int fill_color = ta.getColor(R.styleable.BouncingView_fill_color, Color.WHITE);
            mMaxArcHeight = ta.getDimensionPixelSize(R.styleable.BouncingView_arc_max_height, 100);

            mPaint.setColor(fill_color);

            ta.recycle();
        }
    }

    public void show() {
        if (animationListener != null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    animationListener.showContent();
                }
            },600);
        }

        mStatus = Status.STATUS_SMOOTH_UP;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mMaxArcHeight);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                if (mArcHeight == mMaxArcHeight) {
                    bounce();
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }

    protected void bounce() {
        mStatus = Status.STATUS_DOWN;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMaxArcHeight, 0);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentPointY = 0;

        switch (mStatus) {
            case NONE:
                currentPointY = 0;
                break;
            case STATUS_SMOOTH_UP:
                currentPointY = (int) (getHeight() * (1 - (float) mArcHeight / mMaxArcHeight) + mMaxArcHeight);
                break;
            case STATUS_DOWN:
                currentPointY = mMaxArcHeight;
                break;
        }

        mPath.reset();
        mPath.moveTo(0, currentPointY);
        mPath.quadTo(getWidth() / 2, currentPointY - mArcHeight, getWidth(), currentPointY);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    public void setAnimationListener(AnimationListener listener) {
        this.animationListener = listener;
    }

    public interface AnimationListener {
        void showContent();
    }
}
