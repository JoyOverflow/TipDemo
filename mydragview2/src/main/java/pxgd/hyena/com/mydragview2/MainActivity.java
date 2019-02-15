package pxgd.hyena.com.mydragview2;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {



    @InjectView(R.id.fl_child)
    FrameLayout flChild;
    @InjectView(R.id.fl_parent)
    FrameLayout flParent;


    int margin;
    private FloatTouchListener mFloatTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        int a=Build.VERSION.SDK_INT;

        initBarColor();
        setTouchListener();
    }


    /*
    @OnClick({R.id.fl_child, R.id.fl_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_child:
                break;
            case R.id.fl_parent:
                break;
        }
    }
    */


    private void setTouchListener() {

        flChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*
                Toast.makeText(MainActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
                TranslateAnimation animation = new TranslateAnimation(30, -30, 0, 0);
                animation.setInterpolator(new OvershootInterpolator());
                animation.setDuration(100);
                animation.setRepeatCount(3);
                animation.setRepeatMode(Animation.REVERSE);
                flChild.startAnimation(animation);
                       */
            }

        });

        margin = (int) (10 * getResources().getDisplayMetrics().density + 0.5f);
        mFloatTouchListener = new FloatTouchListener(this, flParent, flChild, margin);
        flChild.setOnTouchListener(mFloatTouchListener);
    }

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
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
    }




}
