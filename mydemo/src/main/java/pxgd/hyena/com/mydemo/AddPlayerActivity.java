package pxgd.hyena.com.mydemo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AddPlayerActivity extends AppCompatActivity {

    List<String> players;

    //数据库对象
    PlayerDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        //创建数据库对象
        mDbHelper = new PlayerDbHelper(AddPlayerActivity.this);
        db = mDbHelper.getWritableDatabase();

        //从数据库获取数据集
        players = mDbHelper.getAllPlayerName(db);

        //绑定列表视
        ListView lv = findViewById(R.id.add_player_list_view);

        //创建数组适配器对象（用数据库读取的泛型集合绑定它）
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AddPlayerActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                players
        );
        lv.setAdapter(adapter);

        //为按钮添加事件
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //得到编辑框的值
                TextView nameTV = findViewById(R.id.edit_add_name);
                String playerName = nameTV.getText().toString();

                //构建键值对
                ContentValues values = new ContentValues();
                values.put(MyContract.PlayerEntry.COLUMN_PLAYER, playerName);
                values.put(MyContract.PlayerEntry.COLUMN_SEX, "Male");
                values.put(MyContract.PlayerEntry.COLUMN_AGE, "42");

                //插入新行（返回新行的自增字段值）
                long newRowId= db.insert(
                        MyContract.PlayerEntry.TABLE_NAME,
                        null,
                        values
                );
                //新值添加到集合中
                players.add(0, playerName);
                //适配器调用此方法表示数据已改变（通过getView来刷新每Item的内容）
                adapter.notifyDataSetChanged();
            }
        });





    }
}
