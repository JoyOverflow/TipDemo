package pxgd.hyena.com.mydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TimeReceiver receiver;
    IntentFilter filter;
    public static final String NEW_STATUS_INTENT = "MY.NEW_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        receiver = new TimeReceiver();
        filter = new IntentFilter( NEW_STATUS_INTENT );


        //设置按钮事件处理
        findViewById(R.id.btnAddPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        AddPlayerActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnShowPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        ShowPlayerActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnFlavor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        FlavorActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnShowPhotos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        PhotoActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnShowContacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        ContactActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnOperation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        OperationActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.jianshu.com/p/ca3d87a4cdf3
                //https://www.cnblogs.com/kexing/p/8331359.html
                //https://www.cnblogs.com/lyd447113735/p/8410755.html

                //发送自定义广播
                Intent  i = new Intent(NEW_STATUS_INTENT);
                i.putExtra("com.yamba.id", 104259);
                sendBroadcast(i);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }
    class TimeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(
                    context,
                    "TimeReceiver!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
