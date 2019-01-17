package pxgd.hyena.com.doudizhu.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 所有活动的基类
 */
public class BaseActivity extends AppCompatActivity {

    protected final static String Tag = "My_DouDiZhu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    /**
     * 活动显示到前台时发出声音
     */
    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.getInstance().play("cancel.mp3");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(Tag, "onBackPressed!");
        finish();
    }

}
