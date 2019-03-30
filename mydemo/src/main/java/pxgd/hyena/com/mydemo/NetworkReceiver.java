package pxgd.hyena.com.mydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //当前网络是否离线
        boolean isDown = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                false
        );
        if (isDown) {
            Toast.makeText(
                    context,
                    "onReceive: NOT connected",
                    Toast.LENGTH_SHORT
            ).show();
        }
        else {
            //获取网络连接服务
            ConnectivityManager connManager =(ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //判断使用的网络类型
            NetworkInfo.State wifi = null;
            NetworkInfo.State gprs = null;
            try {
                wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            } catch (Exception e) { }
            try {
                gprs = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            } catch (Exception e) {}

            String desc;
            if (wifi != null && wifi == NetworkInfo.State.CONNECTED)
                desc="onReceive: wifi connected";
            else if (gprs != null && gprs == NetworkInfo.State.CONNECTED)
                desc="onReceive: gprs connected";
            else
                desc="onReceive: connected";

            Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
        }
    }
}
