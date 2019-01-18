package pxgd.hyena.com.mydemo;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView
        implements
        SurfaceHolder.Callback,
        Runnable
{

    private Thread gameThread=null;
    private Thread drawThread=null;
    private SurfaceHolder surfaceHolder;
    protected final static String Tag = "My_DouDiZhu";

    public GameSurfaceView(Context context, Handler handler, ScreenType screenType) {
        super(context);
        Log.i(Tag, "gameSurfaceView!");


        //当前视图获得焦点
        this.setFocusable(true);
        //保持屏幕常亮
        this.setKeepScreenOn(true);


        //使SurfaceHolder.Callback运行
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }

    /**
     * 创建surface时调用（如启动绘图的线程）
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(Tag, "surfaceCreated!");


        gameThread=new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(Tag, "gameThread run!");
            }
        });
        gameThread.start();

        //启动线程
        drawThread=new Thread(this);
        drawThread.start();
    }

    /**
     * 改变surface尺寸时调用（如横竖屏切换）
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(Tag, "surfaceChanged!");
    }

    /**
     * 销毁surface时调用（如退出游戏前,需在此停止绘图线程）
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(Tag, "surfaceDestroyed!");
    }
    @Override
    public void run() {
        Log.i(Tag, "drawThread run!");
    }
}
