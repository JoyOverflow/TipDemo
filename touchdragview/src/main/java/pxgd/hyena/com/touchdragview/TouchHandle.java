package pxgd.hyena.com.touchdragview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

public class TouchHandle implements View.OnTouchListener{

    private View mParentView;
    private View mFloatView;
    private int mMargin;

    private FrameLayout.LayoutParams mFloatViewWindowParam;
    private Interpolator mInterpolator;
    private int mTouchSlop;

    private int mDownPointerId = -1;
    private float mPreviousX = -1;
    private float mPreviousY = -1;
    private boolean mHasMoved = false;
    private FloatAnimatorListener mUpdateListener;

    public TouchHandle(Context context, View parentView, View floatView, int margin) {
        mParentView = parentView;
        mFloatView = floatView;
        mMargin = margin;

        mFloatViewWindowParam = (FrameLayout.LayoutParams) floatView.getLayoutParams();
        mInterpolator = new DecelerateInterpolator();

        //getScaledTouchSlop是一个距离（手移动距离要大于此值时才开始移动控件）
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String message="";

        //仅探测单点触控
        int action =event.getAction();

        //可探测到多点触控
        //int action = event.getActionMasked();
        boolean result = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                //移动前的坐标
                mPreviousX = event.getX();
                mPreviousY = event.getY();

                //得到当前触摸点的Index和ID
                int index = event.getActionIndex();
                mDownPointerId = event.getPointerId(index);
                message=String.format("onTouch DOWN (id = %s)", mDownPointerId);
                Log.d(MainActivity.TAG, message);

                break;
            case MotionEvent.ACTION_MOVE:

                int m_index = event.getActionIndex();
                int m_id = event.getPointerId(m_index);
                message=String.format("onTouch MOVE (id = %s)", m_id);
                Log.d(MainActivity.TAG, message);


                if (mDownPointerId >= 0) {
                    if (m_id == mDownPointerId) {

                        //返回false表示视图其实并未移动
                        boolean update = adjustMarginParams(v, event);
                        if (!update) {
                            break;
                        }

                        //使视图重新测量和定位
                        mFloatView.requestLayout();

                        //是否正在移动
                        Log.d(MainActivity.TAG, "onTouch MOVED");
                        mHasMoved = true;
                    }
                }
                result = true;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(MainActivity.TAG, "onTouch UP");

                /*
                if (mDownPointerId >= 0 && mHasMoved) {
                    event.setAction(MotionEvent.ACTION_CANCEL);

                    adjustMarginParams(v, event);

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

                        //插值动画
                        ValueAnimator animator = ValueAnimator.ofInt(x, destX);
                        animator.setInterpolator(mInterpolator);
                        if (mUpdateListener == null) {
                            mUpdateListener = new FloatAnimatorListener();
                            mUpdateListener.setUpdateView(TouchHandle.this);
                        }
                        animator.addUpdateListener(mUpdateListener);
                        animator.setDuration(300);
                        animator.start();
                    }
                }*/

                resetStatus();
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(MainActivity.TAG, "onTouch CANCEL");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(MainActivity.TAG, "onTouch POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(MainActivity.TAG, "onTouch POINTER_UP");
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
    private boolean adjustMarginParams(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        float deltaX = x - mPreviousX;
        float deltaY = y - mPreviousY;

        //并未移动视图
        if (!mHasMoved) {
            if (Math.abs(deltaX) < mTouchSlop && Math.abs(deltaY) < mTouchSlop) {
                return false;
            }
        }

        //
        if ((mFloatViewWindowParam.gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
            mFloatViewWindowParam.topMargin =
                    mParentView.getBottom() - mMargin - mFloatView.getHeight();
        }
        if ((mFloatViewWindowParam.gravity & Gravity.RIGHT) == Gravity.RIGHT) {
            mFloatViewWindowParam.leftMargin =
                    mParentView.getRight() - mMargin - mFloatView.getWidth();
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


    /**
     * 内部类
     */
    public class FloatAnimatorListener implements ValueAnimator.AnimatorUpdateListener{
        private WeakReference<TouchHandle> mListener;

        public void setUpdateView(TouchHandle listener) {
            mListener = new WeakReference<>(listener);
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Integer value = (Integer) animation.getAnimatedValue();
            TouchHandle listener = null;
            if (mListener == null || (listener = mListener.get()) == null) {
                return;
            }
            listener.mFloatViewWindowParam.leftMargin = value;
            mFloatView.requestLayout();
        }
    }

}
