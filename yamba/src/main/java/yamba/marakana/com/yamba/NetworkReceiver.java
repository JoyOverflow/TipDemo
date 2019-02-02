package yamba.marakana.com.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {

    public static final String TAG = "NetworkReceiver";
    public NetworkReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {

        //当前的网络状态
        boolean isNetworkDown = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

        if (isNetworkDown) {
            Log.d(TAG, "网络未连接，停止服务！");
            context.stopService(new Intent(context, UpdaterService.class));
        } else {
            Log.d(TAG, "网络已连接，启动服务！");
            context.startService(new Intent(context, UpdaterService.class));
        }
    }
}
