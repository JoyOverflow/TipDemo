
package pxgd.hyena.com.mydemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PlayerDbHelper extends SQLiteOpenHelper {

    //数据库版本和文件名称
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PlayerDemo.db";

    private static final String TEXT_TYPE = " TEXT";//TODO: test the integer type
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MyContract.PlayerEntry.TABLE_NAME + " (" +
                    MyContract.PlayerEntry._ID + " INTEGER PRIMARY KEY," +
                    MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME + TEXT_TYPE + COMMA_SEP +
                    MyContract.PlayerEntry.COLUMN_NAME_SEX + TEXT_TYPE + COMMA_SEP +
                    MyContract.PlayerEntry.COLUMN_NAME_AGE + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MyContract.PlayerEntry.TABLE_NAME;

    public PlayerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * 返回查询的结果（游标）
     * @param db
     * @return
     */
    public Cursor getAllPlayerCursor(SQLiteDatabase db) {

        //实际要返回的字段列表
        String[] projection = {
                MyContract.PlayerEntry._ID,
                MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME,
                MyContract.PlayerEntry.COLUMN_NAME_SEX,
                MyContract.PlayerEntry.COLUMN_NAME_AGE
        };
        //排序的字段
        String sortOrder =
                MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME + " DESC";

        //查询得到游标
        Cursor c = db.query(
                MyContract.PlayerEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        return c;
    }

    /**
     * 通过游标转换为泛型
     * @param db
     * @return
     */
    public List<String> getAllPlayerName(SQLiteDatabase db) {
        Cursor c = getAllPlayerCursor(db);
        c.moveToFirst();
        List<String> list = new ArrayList<String>();
        while (c.isAfterLast() == false) {
            String name = c.getString(
                    c.getColumnIndexOrThrow(
                            MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME
                    )
            );
            list.add(name);
            c.moveToNext();
        }
        return list;
    }
}