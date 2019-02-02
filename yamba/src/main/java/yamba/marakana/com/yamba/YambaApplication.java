package yamba.marakana.com.yamba;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import winterwell.jtwitter.Twitter;

/**
 * Created by oupxgd on 2017/11/1.
 */
public class YambaApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private static final String TAG = YambaApplication.class.getSimpleName();
    public Twitter twitter;
    private SharedPreferences prefs;
    private boolean serviceRunning;
    private StatusData statusData;

    /**
     * 在应用任何部分第一次运行时触发
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //获取共享首选项对象(每个应用都有自己唯一的共享首选项对象)
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //将本类注册为共享首选项对象的改变监听器(因此本类需实现监听器接口)
        this.prefs.registerOnSharedPreferenceChangeListener(this);

        //数据库操作类
        this.statusData = new StatusData(this);
        Log.d(TAG, "应用程序类已创建（Application）");
    }
    /**
     * 共享首选项数据发生变化时触发
     * @param sharedPreferences
     * @param s
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
    {
        //将twitter设为null
        //则下次调用getTwitter()时，就会重新根据首选项数据来新生成
        this.twitter = null;
    }
    /**
     * 在应用退出前进行清理工作
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "应用程序类已终止（Application）");
    }



    /**
     * 连接Twitter服务端返回twitter对象
     * @return
     */
    public synchronized Twitter getTwitter() {
        if (this.twitter == null) {
            //从获取首选项对象中数据getString(键名称,键不存在时的默认值)
            String username = this.prefs.getString("username", "");
            String password = this.prefs.getString("password", "");
            String apiRoot = this.prefs.getString("apiRoot", "http://yamba.marakana.com/api");
            //apiRoot = "http://yamba.marakana.com/api";
            if (!TextUtils.isEmpty(username) &&
                    !TextUtils.isEmpty(password)
                    && !TextUtils.isEmpty(apiRoot))
            {
                this.twitter = new Twitter(username, password);
                this.twitter.setAPIRootUrl(apiRoot);
            }
        }
        return this.twitter;
    }

    /**
     * 返回数据库操作对象
     */
    public StatusData getStatusData() {
        return statusData;
    }
    public boolean isServiceRunning() {
        return serviceRunning;
    }
    public void setServiceRunning(boolean serviceRunning) {
        this.serviceRunning = serviceRunning;
    }
    public SharedPreferences getPrefs() {
        return prefs;
    }
    //在同一时刻只能进入一个线程
    public synchronized int fetchStatusUpdates() {
        try {
            //获取数据源
            List<String> times=new ArrayList<>();
            times.add("男子毫无征兆刀捅小姨子,妻子跪求家人原谅");
            times.add("北京赏秋,不去郊区也能捕捉最美的秋色");
            times.add("花蛤不只能煮,这样爆炒也是巨好吃");
            times.add("秋季进补,给宝宝的一碗田园鸡汤");
            times.add("大雄和静香的婚后生活,是没羞没躁还是穷的冒泡");
            times.add("溥仪一生为啥最恨太监");
            times.add("看,辣眼睛的动静脉时装周天天都在过万圣节");
            times.add("尴尬,谷歌AI竟把乌龟识别成步枪");
            times.add("央视段子手朱广权新作出炉");

            //long latestStatusCreatedAtTime = this.getStatusData().getLatestStatusCreatedAtTime();

            ContentValues values = new ContentValues();

            int count = 1;
            for (String t : times) {
                long createdAt = new Date().getTime();
                values.put("id", count);
                values.put("name", "ouyj");
                values.put("title", t);
                values.put("created", createdAt);

                //插入一条数据
                this.getStatusData().insertOrIgnore(values);
                //if (latestStatusCreatedAtTime < createdAt) {
                    count++;
                //}
            }
            Log.d(TAG, "插入数据库（Application）");
            return count;
        } catch (RuntimeException e) {
            Log.e(TAG, "插入数据库时发生异常（Application）", e);
            return 0;
        }
    }
}
