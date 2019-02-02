package yamba.marakana.com.yamba;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class StatusProvider extends ContentProvider {

    private static final String TAG = StatusProvider.class.getSimpleName();
    public static final Uri CONTENT_URI = Uri.parse("content://com.yamba.statusprovider");
    public static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.marakana.yamba.status";
    public static final String MULTIPLE_RECORDS_MIME_TYPE = "vnd.android.cursor.dir/vnd.marakana.yamba.status";

    StatusData statusData;
    public StatusProvider() {}


    /**
     * 获取到Uri中的ID
     * @param uri
     * @return
     */
    private long getId(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null) {
            try {
                return Long.parseLong(lastPathSegment);
            } catch (NumberFormatException e) {}
        }
        return -1;
    }
    /**
     * 判断是取单个还是取所有记录
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        return this.getId(uri) < 0 ? MULTIPLE_RECORDS_MIME_TYPE
                : SINGLE_RECORD_MIME_TYPE;
    }

    /**
     * 实例化数据库操作对象
     * @return
     */
    @Override
    public boolean onCreate() {
        statusData = new StatusData(getContext());
        return true;
    }









    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
