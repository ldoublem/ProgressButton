package com.ldoublem.progressButton.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

/**
 * Created by lumingmin on 6/3/16.
 */
public class ProgressButton extends View {
    private int progerssButtonDuration = 200;
    private int scaleAnimationDuration = 300;
    private int rotateAnimationDuration = 400;


    private Paint paintRectF;
    private Paint paintText;

    private Paint paintPro;

    private int mStrokeWidth = 0;
    private int mPadding = 0;

    private float mSpac = 0;
    private float mRadius = 0;

    private int mProRadius = 0;

    private float startAngle = 0f;

    private ProgerssButtonAnim mProgerssButtonAnim;
    private ScaleAnimation mProgerssScaleAnim;

    private RotateAnimation mProgerssRotateAnim;


    private String text = "";

    private int bgColor = Color.RED;
    public void setBgColor(int color)
    {
        this.bgColor=color;
    }
    private int textColor = Color.WHITE;
    public void setTextColor(int color)
    {
        this.textColor=color;
    }
    private int proColor = Color.WHITE;
    public void setProColor(int color)
    {
        this.proColor=color;
    }

    public void setButtonText(String s) {
        this.text = s;
        invalidate();
    }

    private boolean mStop = false;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mStrokeWidth = dip2px(2);
        mPadding = dip2px(2);
        mProRadius = getMeasuredHeight() / 5;


        mProgerssButtonAnim = new ProgerssButtonAnim();
        mProgerssRotateAnim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mProgerssRotateAnim.setRepeatCount(-1);
        mProgerssRotateAnim.setInterpolator(new LinearInterpolator());//不停顿
        mProgerssRotateAnim.setFillAfter(true);//停在最后
        paintRectF = new Paint();
        paintRectF.setAntiAlias(true);
        paintRectF.setStyle(Paint.Style.FILL);
        paintRectF.setStrokeWidth(mStrokeWidth);


        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(dip2px(15));
        paintPro = new Paint();
        paintPro.setAntiAlias(true);
        paintPro.setStyle(Paint.Style.STROKE);
        paintPro.setStrokeWidth(mStrokeWidth / 2);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintText.setColor(textColor);
        paintRectF.setColor(bgColor);
        paintPro.setColor(proColor);

        RectF mRectF = new RectF();                           //RectF对象
        mRectF.left = mPadding + mSpac;
        mRectF.top = mPadding;
        mRectF.right = getMeasuredWidth() - mPadding - mSpac;
        mRectF.bottom = getMeasuredHeight() - mPadding;
        mRadius = (getMeasuredHeight() - 2 * mPadding) / 2;

        canvas.drawRoundRect(mRectF, mRadius, mRadius, paintRectF);

        if (mRectF.width() == mRectF.height() && !mStop) {
            setClickable(true);
            RectF mRectFPro = new RectF();
            mRectFPro.left = getMeasuredWidth() / 2.0f - mRectF.width() / 4;
            mRectFPro.top = getMeasuredHeight() / 2.0f - mRectF.width() / 4;
            mRectFPro.right = getMeasuredWidth() / 2.0f + mRectF.width() / 4;
            mRectFPro.bottom = getMeasuredHeight() / 2.0f + mRectF.width() / 4;
            canvas.drawArc(mRectFPro, startAngle, 100, false, paintPro);
        }


        if (mSpac < (getMeasuredWidth() - getMeasuredHeight()) / 2.0f) {

//        if (mRectF.width() > getFontlength(paintText, text)) {
            canvas.drawText(text,
                    getMeasuredWidth() / 2.0f - getFontlength(paintText, text) / 2.0f,
                    getMeasuredHeight() / 2.0f + getFontHeight(paintText, text) / 3.0f,
                    paintText);
//        }
        }

    }


    public void startAnim() {
        mStop = false;
        setClickable(false);
        if (mProgerssButtonAnim != null)
            clearAnimation();
        mProgerssButtonAnim.setDuration(progerssButtonDuration);
        startAnimation(mProgerssButtonAnim);
    }

    public void startProAnim() {
        if (mProgerssRotateAnim != null)
            clearAnimation();
        mProgerssRotateAnim.setDuration(rotateAnimationDuration);
        startAnimation(mProgerssRotateAnim);


    }

    public void stopAnim(final OnStopAnim mOnStopAnim) {
        clearAnimation();
        mStop = true;
        invalidate();
        if (mProgerssScaleAnim != null)
            clearAnimation();
        else {
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);

            int width = wm.getDefaultDisplay().getWidth();

            mProgerssScaleAnim = new ScaleAnimation(1.0f, width / getMeasuredHeight() * 3.5f,
                    1.0f, width / getMeasuredHeight() * 3.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        mProgerssScaleAnim.setDuration(scaleAnimationDuration);
        startAnimation(mProgerssScaleAnim);
        mProgerssScaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                clearAnimation();
                mOnStopAnim.Stop();
                mSpac = 0;
                invalidate();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private class ProgerssButtonAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);


            mSpac = (getMeasuredWidth() - getMeasuredHeight()) / 2.0f * interpolatedTime;
            invalidate();


            if (interpolatedTime == 1.0f)
                startProAnim();


        }
    }


    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();

    }

    public interface OnStopAnim {
        void Stop();
    }


}
