package pxgd.hyena.com.mydemo_2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdaterService extends Service {

    private boolean runFlag = false;
    private Updater updater;
    private static final String TAG = "UpdaterService";

    public UpdaterService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "服务开始创建，仅运行一次!!");
        this.updater = new Updater();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "服务接收到启动请求！！");
        if (!runFlag) {
            this.runFlag = true;
            this.updater.start();
        }
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "服务接收到终止请求！！");

        this.updater.interrupt();
        this.updater = null;
        this.runFlag = false;
    }

    private class Updater extends Thread {
        @Override
        public void run() {
            super.run();

            UpdaterService updaterService = UpdaterService.this;
            while (updaterService.runFlag) {
                try {
                   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String str=df.format(new Date());
                    Log.d(TAG, str);
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    updaterService.runFlag = false;
                }
            }
        }
    }
}
