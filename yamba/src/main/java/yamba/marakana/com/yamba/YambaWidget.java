package yamba.marakana.com.yamba;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class YambaWidget extends AppWidgetProvider {

    private static final String TAG = YambaWidget.class.getSimpleName();


    /**
     * 在小部件状态更新时调用(可指定更新频率)
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //使用内容提供器(传递URI典据得到游标对象)
        Cursor c = context.getContentResolver().query(StatusProvider.CONTENT_URI,
                null, null, null, null);
        try {
            //将游标先指向第1条记录
            if (c.moveToFirst()) {
                //从游标中取出数据
                CharSequence user = c.getString(c.getColumnIndex(StatusData.C_USER));
                CharSequence createdAt = DateUtils.getRelativeTimeSpanString(context, c.getLong(
                        c.getColumnIndex(StatusData.C_CREATED_AT)
                ));
                CharSequence message = c.getString(c.getColumnIndex(StatusData.C_TEXT));

                // 更新所有Yamba小部件(因为用户有可能在屏幕上挂载多个同样的小部件)
                for (int appWidgetId : appWidgetIds) {
                    Log.d(TAG, "Updating widget " + appWidgetId);

                    //小部件运行于主屏幕应用程序里(RemoteViews是专为小部件设计的视图结构)
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.yamba_widget);
                    views.setTextViewText(R.id.textUser, user);
                    views.setTextViewText(R.id.textCreatedAt, createdAt);
                    views.setTextViewText(R.id.textText, message);
                    views.setOnClickPendingIntent(R.id.yamba_icon,
                            PendingIntent.getActivity(context, 0,
                                    new Intent(context, TimelineActivity.class), 0));
                    //通知系统更新小部件(异步操作)
                    appWidgetManager.updateAppWidget(appWidgetId, views); // <9>
                }
            } else {
                Log.d(TAG, "No data to update");
            }
        } finally {
            //释放内容提供器对象
            c.close();
        }
        Log.d(TAG, "YambaWidget Updated！");
    }
    /**
     * 接收更新服务发送的广播
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //检查接收到的意图标识
        if (intent.getAction().equals(UpdaterService.NEW_STATUS_INTENT)) {
            //触发类内的onUpdate
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            this.onUpdate(context, appWidgetManager, appWidgetManager
                    .getAppWidgetIds(new ComponentName(context, YambaWidget.class)));
        }
    }
}

