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

public class ShowPlayerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PLAYER_LOADER = 0;
    private static final String TAG = "ShowPlayerActivity";
    PlayerDbHelper mDbHelper;
    SQLiteDatabase db;


    ListView lv;
    SimpleCursorAdapter mAdapter;

    //要访问的字段
    String[] projection = {
            MyContract.PlayerEntry._ID,
            MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME,
            MyContract.PlayerEntry.COLUMN_NAME_SEX,
            MyContract.PlayerEntry.COLUMN_NAME_AGE
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

        mAdapter = new SimpleCursorAdapter(
                ShowPlayerActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                null,
                new String[]{MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME},
                new int[]{android.R.id.text1},
                0
        );

        //绑定适配器
        lv =findViewById(R.id.lv_show_players);
        lv.setAdapter(mAdapter);
        getLoaderManager().initLoader(PLAYER_LOADER, null, this);
    }


    /**
     * 创建Loader对象
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.d(TAG, "onCreateLoader!");

        //要访问数据库的uri地址
        Uri uri = Uri.parse("content://yui_player");
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
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished!");
        mAdapter.swapCursor(data);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset!");
        mAdapter.swapCursor(null);
    }
}
