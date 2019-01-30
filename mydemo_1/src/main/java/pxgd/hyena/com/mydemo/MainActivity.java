package pxgd.hyena.com.mydemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GameSurfaceView gameview;
    private ScreenType screenType;
    private Handler handler=new Handler(){};
    protected final static String Tag = "My_DouDiZhu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载游戏界面
        screenType=ScreenType.large;
        gameview=new GameSurfaceView(this,handler,screenType);
        setContentView(gameview);

        //Log.i(Tag, "mainActivity_onCreate!");
    }



}
