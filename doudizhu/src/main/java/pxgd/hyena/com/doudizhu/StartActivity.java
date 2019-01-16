package pxgd.hyena.com.doudizhu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import pxgd.hyena.com.doudizhu.app.MyApplication;

/**
 * 游戏起始界面
 */
public class StartActivity extends AppCompatActivity {

    private MyApplication app;

    public StartActivity() {
        app= MyApplication.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
}
