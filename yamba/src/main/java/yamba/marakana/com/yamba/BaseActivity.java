package yamba.marakana.com.yamba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 活动的超类
 */
public class BaseActivity extends AppCompatActivity {

    //可访问的应用类对象
    YambaApplication yamba;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取应用类对象
        yamba = (YambaApplication) getApplication();
    }

    /**
     * 在第一次按下菜单按钮时调用，只要活动不撤销菜单就一直在内存中
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    /**
     * 在菜单打开展示时触发
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //根据服务是否已运行来改变菜单项的文本和图标
        MenuItem toggleItem = menu.findItem(R.id.itemToggleService);
        if (yamba.isServiceRunning()) {
            toggleItem.setTitle(R.string.titleServiceStop);
            toggleItem.setIcon(android.R.drawable.ic_media_pause);
        } else {
            toggleItem.setTitle(R.string.titleServiceStart);
            toggleItem.setIcon(android.R.drawable.ic_media_play);
        }
        return true;
    }
    /**
     * 每点击一次菜单项就调用一次
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itemToggleService:
                //启动服务
                Log.i(TAG, "发送服务启动/停止请求！！");
                if (yamba.isServiceRunning())
                    stopService(new Intent(this, UpdaterService.class));
                else
                    startService(new Intent(this, UpdaterService.class));
                break;
            case R.id.itemPrefs:
                //启动新的活动
                startActivity(new Intent(this, PrefsActivity.class));
                break;
        }
        return true;
    }
}
