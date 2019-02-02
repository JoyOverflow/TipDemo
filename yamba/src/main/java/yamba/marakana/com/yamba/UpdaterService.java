package yamba.marakana.com.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oupxgd on 2017/11/1.
 */

public class UpdaterService extends Service {
    private static final String TAG = "UpdaterService_Log";
    public static final String NEW_STATUS_INTENT = "NEW_STATUS";
    public static final String NEW_STATUS_EXTRA_COUNT = "NEW_STATUS_EXTRA_COUNT";

    private Updater updater;
    private boolean runFlag = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 服务创建时调用,仅运行一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "服务开始创建，仅运行一次!!");
        this.updater = new Updater();
    }

    /**
     * 创建成功后的服务，其每收到一次start意图时就会调用一次
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "服务接收到启动请求！！");
        if (!runFlag) {
            this.runFlag = true;
            this.updater.start();
            ((YambaApplication) super.getApplication()).setServiceRunning(true);
        }
        return Service.START_STICKY;
    }
    /**
     * 收到stop意图后，在销毁服务前调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "服务接收到终止请求！！");
        this.updater.interrupt();
        this.updater = null;
        this.runFlag = false;
        ((YambaApplication) super.getApplication()).setServiceRunning(false);
    }

    //抓取网络数据的工作线程
    private class Updater extends Thread {
        Intent intent;
        static final String RECEIVE_TIMELINE_NOTIFICATIONS = "android.yamba.RECEIVE_TIMELINE_NOTIFICATIONS";
        List<String> times;
        public Updater() {
            times=new ArrayList<>();
        }
        @Override
        public void run() {
            super.run();
            UpdaterService updaterService = UpdaterService.this;

            while (updaterService.runFlag) {
                Log.d(TAG, "工作线程运行中（周期10秒）！！");
                try {
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String str=df.format(new Date());
//                    times.add(str);
//                    for (String s : times) {
//                        Log.d(TAG, String.format("%s", s));
//                    }

                    YambaApplication yamba = (YambaApplication) updaterService.getApplication();
                    int newUpdates=yamba.fetchStatusUpdates();
                    if (newUpdates > 0) {
                        Log.d(TAG, "工作线程往数据库内插入数据！！");

                        //发送带标识的意图
                        intent = new Intent(NEW_STATUS_INTENT);
                        intent.putExtra(NEW_STATUS_EXTRA_COUNT, newUpdates);
                        //广播接收者必需具有指定的权限
                        updaterService.sendBroadcast(intent, RECEIVE_TIMELINE_NOTIFICATIONS);
                    }
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    updaterService.runFlag = false;
                }
            }
        }
    }
}
