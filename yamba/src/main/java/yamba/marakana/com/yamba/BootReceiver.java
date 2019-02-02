package yamba.marakana.com.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //启动服务
        context.startService(new Intent(context, UpdaterService.class)); // <3>
        Log.d("BootReceiver", "onReceived");
    }
}
