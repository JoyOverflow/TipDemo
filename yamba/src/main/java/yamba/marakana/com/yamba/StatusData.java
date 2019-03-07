package yamba.marakana.com.yamba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by oupxgd on 2017/11/3.
 */

public class StatusData {

    //内部类的成员变量字段
    private static final String TAG = StatusData.class.getSimpleName()+"_Logs";
    static final int DB_VERSION = 1;
    static final String DB_NAME = "mytimes.db";
    static final String TABLE = "timeList";


    public static final String C_ID = "id";
    public static final String C_CREATED_AT = "created";
    public static final String C_TEXT = "title";
    public static final String C_USER = "name";

    private final DbHelper dbHelper;

    /**
     * 构造函数(实例化数据库类)
     */
    public StatusData(Context context) {
        this.dbHelper = new DbHelper(context);
        Log.i(TAG, "初始化数据库操作类（StatusData）");
    }
    /**
     * 关闭数据库
     */
    public void close() {
        this.dbHelper.close();
    }
    /**
     *向数据库插入数据
     */
    public void insertOrIgnore(ContentValues values) {
        Log.d(TAG, "插入数据（StatusData）！！");

        //打开数据库
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try {
            //插入数据并忽略冲突
            db.insertWithOnConflict(TABLE, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
        } finally {
        //保证使用完数据库后对其及时关闭
        db.close();
    }
    }
    /**
     *返回数据库的所有记录
     */
    public Cursor getStatusUpdates() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        //查询数据,返回一个游标
        return db.query(TABLE, null, null, null, null, null, "created desc");
    }
    /**
     *返回最后一条记录的时间戳
     */
    public long getLatestStatusCreatedAtTime() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        try {
            String[] MAX_CREATED_AT_COLUMNS = { "max(created)" };
            Cursor cursor = db.query(TABLE, MAX_CREATED_AT_COLUMNS,
                    null, null, null, null, null);
            try {
                return cursor.moveToNext() ? cursor.getLong(0) : Long.MIN_VALUE;
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }
    /**
     *返回指定id的记录
     */
    public String getStatusTextById(long id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        try {
            String[] DB_TEXT_COLUMNS = { "title" };
            Cursor cursor = db.query(TABLE, DB_TEXT_COLUMNS, "ID=" + id,
                    null, null, null, null);
            try {
                return cursor.moveToNext() ? cursor.getString(0) : null;
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    /**
     * 私有内部数据库类
     */
    class DbHelper extends SQLiteOpenHelper {

        //(1)构造函数:将常量（数据库文件名称和版本）传给父类
        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        //(2)数据库第一次创建时触发
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "创建数据表结构（DbHelper）");
            String sql = "create table " + TABLE + "(id int primary key,name text,title text,created int)";
            db.execSQL(sql);
        }

        //(3)版本old!=new时触发
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "升级数据表结构（DbHelper）");
            //删除并重建数据表
            db.execSQL("drop table if exists times");
            onCreate(db);
        }

        /*(4)DbHelper类的getWritableDatabase和getReadableDatabase会返回SQLiteDatabase对象
        而close关闭数据库文件
        SQLiteDatabase对象(db)
        1.execSQL执行语句
        2.insertWithOnConflict插入键值对
        3.query查询记录，返回cursor
        4.close关闭数据库文件
         */
    }
}
