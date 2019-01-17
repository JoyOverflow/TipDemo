package pxgd.hyena.com.doudizhu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pxgd.hyena.com.doudizhu.app.BaseActivity;
import pxgd.hyena.com.doudizhu.app.MyApplication;
import pxgd.hyena.com.doudizhu.util.DialogUtil;

/**
 * 游戏起始界面
 */
public class StartActivity extends BaseActivity
        implements View.OnClickListener {

    private MyApplication app;
    /**
     * 构造
     */
    public StartActivity() {
        app= MyApplication.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //绑定按钮单击事件
        findViewById(R.id.start_screen_start).setOnClickListener(this);
        findViewById(R.id.start_screen_feedback).setOnClickListener(this);
        findViewById(R.id.start_screen_exit).setOnClickListener(this);


        //播放背景音乐
        app.playbgMusic("welcome.ogg");
    }



    /**
     * 加载菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /**
     * 菜单项被点击
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //播放音乐特效
        app.play("ok.ogg");

        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_exits:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        app.play("ok.ogg");
        switch (v.getId()) {
            case R.id.start_screen_start:
                startActivity(new Intent(this,SelectActivity.class ));
                break;
            case R.id.start_screen_feedback:
                break;
            case R.id.start_screen_exit:
                //退出应用
                DialogUtil.exitSystemDialog(this);
                break;
        }
    }
}
