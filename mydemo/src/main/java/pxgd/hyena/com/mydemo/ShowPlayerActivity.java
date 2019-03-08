package pxgd.hyena.com.mydemo;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowPlayerActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PLAYER_LOADER = 0;
    private static final String TAG = "ShowPlayerActivity";

    //数据库对象
    PlayerDbHelper mDbHelper;
    SQLiteDatabase db;

    ListView lv;

    //游标适配器对象
    SimpleCursorAdapter mAdapter;

    //要访问的字段
    String[] projection = {
            MyContract.PlayerEntry._ID,
            MyContract.PlayerEntry.COLUMN_PLAYER,
            MyContract.PlayerEntry.COLUMN_SEX,
            MyContract.PlayerEntry.COLUMN_AGE
    };
    //排序的字段
    String sortOrder =
            MyContract.PlayerEntry._ID /*+ " DESC"*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_player);

        //读取数据库
        mDbHelper = new PlayerDbHelper(this);
        db = mDbHelper.getReadableDatabase();

        //创建游标适配器对象
        mAdapter = new SimpleCursorAdapter(
                ShowPlayerActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                null,
                new String[]{MyContract.PlayerEntry.COLUMN_PLAYER},
                new int[]{android.R.id.text1},
                0
        );

        //绑定适配器
        lv =findViewById(R.id.lv_show_players);
        lv.setAdapter(mAdapter);

        //加载器执行
        getLoaderManager().initLoader(PLAYER_LOADER, null, this);
    }


    /**
     * 创建Loader对象（执行数据加载）
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.d(TAG, "onCreateLoader!");

        //要访问数据库的uri地址
        Uri uri = Uri.parse("content://ouyj_provider");
        switch (id) {
            case PLAYER_LOADER:
                //返回一个CursorLoader对象
                return new CursorLoader(
                        ShowPlayerActivity.this,
                        uri,
                        projection,
                        null,
                        null,
                        sortOrder
                );
            default:
                return null;
        }
    }

    /**
     * 异步加载完后触发（结果数据集位于游标参数中）
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished!");
        //通过更换游标来更新数据内容
        mAdapter.swapCursor(data);
    }
    /**
     * 加载器被重置（数据无效）
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset!");
        //释放游标
        mAdapter.swapCursor(null);
    }
}
