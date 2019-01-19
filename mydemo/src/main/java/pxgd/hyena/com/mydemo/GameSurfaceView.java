package pxgd.hyena.com.mydemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class GameSurfaceView extends SurfaceView
        implements
        SurfaceHolder.Callback,
        Runnable
{

    private ScreenType screenType;
    private Canvas canvas;
    private Thread gameThread=null;
    private Thread drawThread=null;
    private SurfaceHolder surfaceHolder;
    protected final static String Tag = "My_DouDiZhu";

    //屏幕宽高度
    private	int screen_height=0;
    private	int screen_width=0;
    //背景图像
    private Bitmap bgBitmap;
    private AssetManager assetManager;



    public GameSurfaceView(Context context, Handler handler, ScreenType screenType) {
        super(context);
        Log.i(Tag, "gameSurfaceView!");

        assetManager=context.getAssets();
        this.screenType=screenType;
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

        //获取屏幕高宽度
        screen_height = this.getHeight();
        screen_width = this.getWidth();

        //初始化加载的具体资源（屏幕类型）
        initBitMap();

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

        onGameDraw();
    }






    private void onGameDraw() {
        synchronized (surfaceHolder) {
            try {
                canvas = surfaceHolder.lockCanvas();
                drawBackground();




            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    private void drawBackground() {
        Rect src = new Rect(0, 0, bgBitmap.getWidth(),bgBitmap.getHeight());
        Rect dst = new Rect(0, 0, screen_width, screen_height);
        canvas.drawBitmap(bgBitmap, src, dst, null);
    }
    public void initLowBitMap() {
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    }
    public void initMiddleBitMap() {
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    }
    public void initLargeBitMap() {
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        try {
            initHeadBitmap=BitmapFactory.decodeStream(assetManager.open("images/logo_unknown.png"));
            exitBitmap=BitmapFactory.decodeStream(assetManager.open("images/game_icon_exit.png"));
            setupBitmap=BitmapFactory.decodeStream(assetManager.open("images/game_icon_setting.png"));
            cardBgBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_back_header.png"));
            prepareButtontextBitmap=BitmapFactory.decodeStream(assetManager.open("images/text_ready.png"));
            prepareButtonupbgBitmap=BitmapFactory.decodeStream(assetManager.open("images/big_green_btn.png"));
            prepareButtonbgBitmap=prepareButtonupbgBitmap;
            prepareButtondownbgBitmap=BitmapFactory.decodeStream(assetManager.open("images/big_green_btn_down.png"));
            prepareButtonokBitmap=BitmapFactory.decodeStream(assetManager.open("images/ready.png"));
            //数字图片
            for(int i=0;i<10;i++){
                numberBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/beishu_"+i+".png")));
            }
            //倍字图像
            beiBitmap=BitmapFactory.decodeStream(assetManager.open("images/game_icon_bei.png"));
            for(int n=0;n<10;n++){
                cardNumberBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/card_count_"+n+".png")));
            }
            for(int n=0;n<13;n++){
                //big_black_1.png
                cardnumblackBitmap[n]=BitmapFactory.decodeStream(assetManager.open("images/big_black_"+(n+1)+".png"));
            }
            for(int n=0;n<13;n++){
                //big_red_1.png
                cardnumredBitmap[n]=BitmapFactory.decodeStream(assetManager.open("images/big_red_"+(n+1)+".png"));
            }
            cardlogoBitmap[0]=BitmapFactory.decodeStream(assetManager.open("images/mark_grass_big.png"));//黑
            cardlogoBitmap[1]=BitmapFactory.decodeStream(assetManager.open("images/mark_peach_big.png"));//黑
            cardlogoBitmap[2]=BitmapFactory.decodeStream(assetManager.open("images/mark_heart_big.png"));//红
            cardlogoBitmap[3]=BitmapFactory.decodeStream(assetManager.open("images/mark_square_big.png"));//红
            dwBitmap=BitmapFactory.decodeStream(assetManager.open("images/dawang_big.png"));
            xwBitmap=BitmapFactory.decodeStream(assetManager.open("images/xiaowang_big.png"));
            dwtopBitmap=BitmapFactory.decodeStream(assetManager.open("images/dawang_header.png"));
            xwtopBitmap=BitmapFactory.decodeStream(assetManager.open("images/xiaowang_header.png"));
            playCardBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_back_small.png"));
            cardFaceBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_gb_big.png"));

            //抢地主
            gramTextBitmap[0]=BitmapFactory.decodeStream(assetManager.open("images/string_bu.png"));
            gramTextBitmap[1]=BitmapFactory.decodeStream(assetManager.open("images/string_chu.png"));
            gramTextBitmap[2]=BitmapFactory.decodeStream(assetManager.open("images/string_di.png"));
            gramTextBitmap[3]=BitmapFactory.decodeStream(assetManager.open("images/string_jiao.png"));
            gramTextBitmap[4]=BitmapFactory.decodeStream(assetManager.open("images/string_qiang.png"));
            gramTextBitmap[5]=BitmapFactory.decodeStream(assetManager.open("images/string_zhu.png"));
            gramTextBitmap[6]=BitmapFactory.decodeStream(assetManager.open("images/text_bj.png"));
            gramTextBitmap[7]=BitmapFactory.decodeStream(assetManager.open("images/text_bq.png"));
            gramTextBitmap[8]=BitmapFactory.decodeStream(assetManager.open("images/text_cue.png"));
            gramTextBitmap[9]=BitmapFactory.decodeStream(assetManager.open("images/text_jdz.png"));
            gramTextBitmap[10]=BitmapFactory.decodeStream(assetManager.open("images/text_pass.png"));
            gramTextBitmap[11]=BitmapFactory.decodeStream(assetManager.open("images/text_qdz.png"));
            gramTextBitmap[12]=BitmapFactory.decodeStream(assetManager.open("images/text_ready.png"));
            gramTextBitmap[13]=BitmapFactory.decodeStream(assetManager.open("images/text_repick.png"));
            gramTextBitmap[14]=BitmapFactory.decodeStream(assetManager.open("images/text_send_card.png"));
            gramTextBitmap[15]=BitmapFactory.decodeStream(assetManager.open("images/blue_btn.png"));
            gramTextBitmap[16]=BitmapFactory.decodeStream(assetManager.open("images/green_btn.png"));
            gramTextBitmap[17]=BitmapFactory.decodeStream(assetManager.open("images/red_btn.png"));
            gramTextBitmap[18]=BitmapFactory.decodeStream(assetManager.open("images/other_btn_disable.png"));

            //头像图标
            HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu.png")));
            HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu_w.png")));
            HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin.png")));
            HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin_w.png")));

            //牌正面背景
            cardbeforeBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_gb_header.png"));
            overGameBitmaps[0]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_lose.png"));
            overGameBitmaps[1]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_win.png"));
            overGameBitmaps[2]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_lose.png"));
            overGameBitmaps[3]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_win.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initBitMap() {
        switch (screenType) {
            case large:
                initLargeBitMap();
                break;
            case middle:
                initMiddleBitMap();
                break;
            case low:
            default:
                initLowBitMap();
                break;
        }
    }



}
