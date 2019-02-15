package pxgd.hyena.com.touchdragview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    int margin;
    FrameLayout flChild;
    FrameLayout flParent;
    private TouchHandle mFloatTouchListener;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBarColor();

        //点击事件监听器
        flChild = findViewById(R.id.fl_child);
        flChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "OnClick");

                //震动效果
                TranslateAnimation animation = new TranslateAnimation(
                        30,
                        -30,
                        0,
                        0
                );
                animation.setInterpolator(new OvershootInterpolator());
                animation.setDuration(100);
                animation.setRepeatCount(3);
                animation.setRepeatMode(Animation.REVERSE);
                flChild.startAnimation(animation);
            }
        });

        //得到屏幕密度（每英寸有多少个像素点）和屏幕宽高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density=getResources().getDisplayMetrics().density;
        margin = (int) (10 * density + 0.5f);

        //触摸事件监听器
        flParent = findViewById(R.id.fl_parent);
        mFloatTouchListener = new TouchHandle(this, flParent, flChild, margin);
        flChild.setOnTouchListener(mFloatTouchListener);
    }
    /**
     * 使视图内容全屏
     */
    private void initBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();

            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN和SYSTEM_UI_FLAG_LAYOUT_STABLE（需结合使用，全屏并覆盖状态栏）
            //SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION（隐藏底部的导航栏）
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);

            //将导航栏和状态栏设置成透明色
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            //设置状态栏的颜色
            //getWindow().setStatusBarColor(Color.RED);
        }
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
