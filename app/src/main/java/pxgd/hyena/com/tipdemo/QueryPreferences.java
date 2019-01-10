package pxgd.hyena.com.tipdemo;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * 读取和写入搜索字串
 */
public class QueryPreferences {

    private static final String PREF_SEARCH_QUERY = "searchQuery";

    /**
     * 获取保存的字串值
     * @param context
     * @return
     */
    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    /**
     * 写入字串数据（异步方式）
     * @param context
     * @param query
     */
    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }
}
