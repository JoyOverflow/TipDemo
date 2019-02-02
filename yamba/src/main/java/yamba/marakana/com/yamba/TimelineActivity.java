package yamba.marakana.com.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineActivity extends BaseActivity {
    Cursor cursor;
    ListView listTimeline;
    SimpleCursorAdapter adapter;
    static final String[] FROM = {"created", "name", "title"};
    static final int[] TO = {R.id.textCreatedAt, R.id.textUser, R.id.textText};

    //内部类（广播接收器）
    TimelineReceiver receiver;
    IntentFilter filter;
    static final String SEND_TIMELINE_NOTIFICATIONS = "com.yamba.SEND_TIMELINE_NOTIFICATIONS";
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //未设置首选项时则提示设置
        if (yamba.getPrefs().getString("username", null) == null) {
            startActivity(new Intent(this, PrefsActivity.class));
            Toast.makeText(this, R.string.msgSetupPrefs, Toast.LENGTH_LONG).show();
        }
        //得到列表视控件
        listTimeline = (ListView) findViewById(R.id.listTimeline);

        //实例化广播接收器内部类以及设置意图过滤器
        receiver = new TimelineReceiver();
        filter = new IntentFilter( UpdaterService.NEW_STATUS_INTENT );
    }

    /**
     * 查询出数据库内的数据
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.setupList();

        //注册广播接收器
        super.registerReceiver(receiver, filter, SEND_TIMELINE_NOTIFICATIONS, null);
    }
    @Override
    protected void onPause() {
        super.onPause();

        //注销广播接收器
        unregisterReceiver(receiver);
    }
    private void setupList() {
        //获取数据(得到一个游标,它是一个迭代器)
        cursor = yamba.getStatusData().getStatusUpdates();
        //通知活动管理游标(活动撤消时会先释放掉游标指向的数据)
        startManagingCursor(cursor);

        //基于游标的适配器
        adapter = new SimpleCursorAdapter(this,
                R.layout.row, //布局文件指定单行数据的显示方式
                cursor, //数据源
                FROM, //要绑定的数据库字段
                TO);//要绑定的布局控件ID

        //在绑定视图时添加业务逻辑
        adapter.setViewBinder(VIEW_BINDER);
        listTimeline.setAdapter(adapter);
    }
    //内部实现接口
    static final SimpleCursorAdapter.ViewBinder VIEW_BINDER = new SimpleCursorAdapter.ViewBinder()
    {
        //接口的方法(在数据与控件绑定时调用)
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (view.getId() != R.id.textCreatedAt)
                return false;

            // 将时间戳转为相对时间
            long timestamp = cursor.getLong(columnIndex);
            CharSequence relTime = DateUtils.getRelativeTimeSpanString(view
                    .getContext(), timestamp);
            ((TextView) view).setText(relTime);

            return true;
        }

    };


    /**
     * 关闭数据库
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        yamba.getStatusData().close();
    }

    //内部类（接收广播）
    class TimelineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setupList();
            Log.d("TimelineReceiver", "onReceived");
        }
    }
}
