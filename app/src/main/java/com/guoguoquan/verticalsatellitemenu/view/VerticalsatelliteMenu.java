package com.guoguoquan.verticalsatellitemenu.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：小段果果 on 2016/3/30 18:51
 * 邮箱：duanyikang@mumayi.com
 */
public class VerticalsatelliteMenu extends FrameLayout {

    private List<View> childViews;
    private int childViewNumber;
    private int childViewHeight;
    private onVericalsatllliteItemListener listener;


    public VerticalsatelliteMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        childViews = new ArrayList<View>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        childViewNumber = this.getChildCount();
        for (int i = 0; i < childViewNumber; i++) {
            View childView = getChildAt(i);
            childViews.add(childView);
        }


        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (childViewNumber != 0) {
            childViewHeight = childViews.get(0).getMeasuredHeight();
        }
        for (int i = 0; i < childViewNumber; i++) {
            if (i < childViewNumber - 1)
                childViews.get(i).layout(getMeasuredWidth() / 2 - childViewHeight / 2, getMeasuredHeight() - childViewHeight * 3 / 2, getMeasuredWidth() / 2 + childViewHeight / 2, getMeasuredHeight() - childViewHeight / 2);
            else
                childViews.get(i).layout(getMeasuredWidth() / 2 - childViewHeight / 2, getMeasuredHeight() - childViewHeight * 3 / 2 - 50, getMeasuredWidth() / 2 + childViewHeight / 2, getMeasuredHeight() - childViewHeight / 2);

        }
    }


    public void open() {
        for (int i = 0; i < childViewNumber; i++) {
            if (i < childViewNumber - 1)
                ObjectAnimator
                        .ofFloat(childViews.get(i), "TranslationY", 0.0F, -i * (childViewHeight * 3 / 2))//
                        .setDuration(300)
                        .start();
            else
                ObjectAnimator
                        .ofFloat(childViews.get(i), "TranslationY", 0.0F, -i * (childViewHeight * 3 / 2))//
                        .setDuration(300)
                        .start();
        }
    }

    public void close() {
        oldindex = 0;
        for (int i = 0; i < childViewNumber; i++) {
            ObjectAnimator
                    .ofFloat(childViews.get(i), "TranslationY", -i * (childViewHeight * 3 / 2), 0.0F)
                    .setDuration(300)
                    .start();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 1f).setDuration(1);
            valueAnimator.start();
            final int finalI = i;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    childViews.get(finalI).setScaleX(cVal);
                    childViews.get(finalI).setScaleY(cVal);
                }
            });
        }
    }

    private static int oldindex = 0;

    private void touch(final int index) {
        if (listener != null) {
            listener.onItemSelect(index);
        }
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(1.0f, 1.5f).setDuration(100);
        valueAnimator1.start();
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float cVal = (Float) animation.getAnimatedValue();
                childViews.get(index).setScaleX(cVal);
                childViews.get(index).setScaleY(cVal);
            }
        });

        if (oldindex != index) {
            ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(1f, 1f).setDuration(100);
            valueAnimator2.start();
            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    childViews.get(oldindex).setScaleX(cVal);
                    childViews.get(oldindex).setScaleY(cVal);
                }
            });
            valueAnimator2.addListener(new ValueAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    oldindex = index;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }
    }

    int now = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getY() < 0) {
            int temp = 0;
            if (childViewHeight != 0)
                temp = (int) Math.abs(event.getY()) / (childViewHeight + childViewHeight / 2);
            if (temp != now && Math.abs(event.getY()) < Math.abs((int) childViews.get(childViewNumber - 1).getTranslationY()) + childViewHeight) {
                now = temp;
                touch(now);
            }
            if (event.getAction() == 1) {
                //listener.onItemOpen(temp);
                oldindex = 0;
                now = -1;
            }
        } else {
            if (event.getAction() == 1) {
                oldindex = 0;
                now = -1;
            }
        }
        return super.onTouchEvent(event);
    }

    public interface onVericalsatllliteItemListener {
        void onItemSelect(int number);

        void onItemOpen(int number);
    }

    public void setOnVericalsatllliteItemListener(onVericalsatllliteItemListener listener) {
        this.listener = listener;
    }

}

