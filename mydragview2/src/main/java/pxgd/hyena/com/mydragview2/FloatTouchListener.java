package pxgd.hyena.com.mydragview2;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

public class FloatTouchListener implements View.OnTouchListener {
    private View mParentView;
    private View mFloatView;
    private FrameLayout.LayoutParams mFloatViewWindowParam;
    private float mPreviousX = -1;
    private float mPreviousY = -1;
    private boolean mHasMoved = false;
    private int mTouchSlop;
    private int mDownPointerId = -1;
    private Interpolator mInterpolator;
    private FloatAnimatorUpdateListener mUpdateListener;
    private int mMargin;

    public FloatTouchListener(Context context, View parentView, View floatView, int margin) {
        mParentView = parentView;
        mFloatView = floatView;
        mFloatViewWindowParam = (FrameLayout.LayoutParams) floatView.getLayoutParams();
        mInterpolator = new DecelerateInterpolator();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMargin = margin;
    }

    private boolean adjustMarginParams(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float deltaX = x - mPreviousX;
        float deltaY = y - mPreviousY;
        if (!mHasMoved) {
            if (Math.abs(deltaX) < mTouchSlop && Math.abs(deltaY) < mTouchSlop) {
                return false;
            }
        }

        if ((mFloatViewWindowParam.gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
            mFloatViewWindowParam.topMargin = mParentView.getBottom() - mMargin - mFloatView
                    .getHeight();
        }

        if ((mFloatViewWindowParam.gravity & Gravity.RIGHT) == Gravity.RIGHT) {
            mFloatViewWindowParam.leftMargin = mParentView.getRight() - mMargin - mFloatView
                    .getWidth();
        }

        mFloatViewWindowParam.gravity = Gravity.NO_GRAVITY;

        //左上角位置
        int newX = (int) (mFloatViewWindowParam.leftMargin + deltaX);
        int newY = (int) (mFloatViewWindowParam.topMargin + deltaY);
        newX = Math.max(newX, mParentView.getLeft() + mMargin);
        newX = Math.min(newX, mParentView.getRight() - mMargin - mFloatView.getWidth());
        newY = Math.max(newY, mParentView.getTop() + mMargin);
        newY = Math.min(newY, mParentView.getBottom() - mMargin - mFloatView.getHeight());
        mFloatViewWindowParam.leftMargin = newX;
        mFloatViewWindowParam.topMargin = newY;

        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        boolean result = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mDownPointerId = MotionEventCompat.getPointerId(event, 0);
                mPreviousX = event.getX();
                mPreviousY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mDownPointerId >= 0) {
                    int index = MotionEventCompat.getActionIndex(event);
                    int id = MotionEventCompat.getPointerId(event, index);
                    if (id == mDownPointerId) {
                        boolean update = adjustMarginParams(view, event);
                        if (!update) {
                            break;
                        }
                        mFloatView.requestLayout();
                        mHasMoved = true;
                        result = true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (mDownPointerId >= 0 && mHasMoved) {
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    adjustMarginParams(view, event);
                    mFloatView.requestLayout();
                    int center = (mParentView.getWidth() - mFloatView.getWidth()) / 2;
                    int x = mFloatViewWindowParam.leftMargin;
                    int destX = 0;
                    if (x < center) {
                        destX = mParentView.getLeft() + mMargin;
                    } else {
                        destX = mParentView.getRight() - mMargin - mFloatView.getWidth();
                    }
                    int deltaHorizon = destX - x;
                    if (Math.abs(deltaHorizon) < 100) {
                        mFloatViewWindowParam.leftMargin = destX;
                        mFloatView.requestLayout();
                    } else {
                        ValueAnimator animator = ValueAnimator.ofInt(x, destX);
                        animator.setInterpolator(mInterpolator);
                        if (mUpdateListener == null) {
                            mUpdateListener = new FloatAnimatorUpdateListener();
                            mUpdateListener.setUpdateView(FloatTouchListener.this);
                        }
                        animator.addUpdateListener(mUpdateListener);
                        animator.setDuration(300);
                        animator.start();
                    }
                }
                resetStatus();
                break;
            }
            default:
                break;
        }
        return result;
    }

    private void resetStatus() {
        mDownPointerId = -1;
        mPreviousX = -1;
        mPreviousY = -1;
        mHasMoved = false;
    }

    private class FloatAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private WeakReference<FloatTouchListener> mListener;

        public void setUpdateView(FloatTouchListener listener) {
            mListener = new WeakReference<FloatTouchListener>(listener);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Integer value = (Integer) animation.getAnimatedValue();
            FloatTouchListener listener = null;
            if (mListener == null || (listener = mListener.get()) == null) {
                return;
            }
            listener.mFloatViewWindowParam.leftMargin = value;
            mFloatView.requestLayout();
        }
    }
}
