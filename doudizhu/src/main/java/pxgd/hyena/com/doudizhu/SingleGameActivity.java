package pxgd.hyena.com.doudizhu;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;

import pxgd.hyena.com.doudizhu.app.BaseActivity;
import pxgd.hyena.com.doudizhu.app.MyApplication;
import pxgd.hyena.com.doudizhu.app.SingleGameView;
import pxgd.hyena.com.doudizhu.bean.ScreenType;
import pxgd.hyena.com.doudizhu.util.DialogUtil;

public class SingleGameActivity extends BaseActivity {

    private ScreenType screenType;
    private SingleGameView gameview;
    private Handler handler=new Handler(){};

    private MyApplication app;
    /**
     * 构造
     */
    public SingleGameActivity() {
        app= MyApplication.getInstance();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_single_game);

        Display display=getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics=new DisplayMetrics();
        display.getMetrics(metrics);
        if(metrics.heightPixels<480){
            screenType=ScreenType.low;
        }else if(metrics.heightPixels>=480&&metrics.heightPixels<720){
            screenType=ScreenType.middle;
        }else if(metrics.heightPixels>=720){
            screenType=ScreenType.large;
        }

        //创建游戏界面视图并加载
        gameview=new SingleGameView(this,handler,screenType);
        setContentView(gameview);

        app.playbgMusic("normal2.ogg");
    }

    /**
     * 活动运行时按下按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /*
        Log.i(Tag,
                "KeyEvent.KEYCODE_BACK:"+KeyEvent.KEYCODE_BACK +
                ",keyCode:"+keyCode+
                ",event.getKeyCode:"+event.getKeyCode()
        );
        */

        //判断是否为返回按钮（设备Back键）
        if(KeyEvent.KEYCODE_BACK==keyCode){
            //显示游戏退出对话框
            DialogUtil.exitGameDialog(this);

            Log.i(Tag, "onKeyDown!");
        }
        return true;
    }

}
