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
    PlayerDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        //创建数据库对象
        mDbHelper = new PlayerDbHelper(AddPlayerActivity.this);
        db = mDbHelper.getWritableDatabase();

        //获取数据集
        players = mDbHelper.getAllPlayerName(db);

        //绑定列表视
        ListView lv = findViewById(R.id.add_player_list_view);
        final ArrayAdapter<String> theAdapter = new ArrayAdapter<>(
                AddPlayerActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                players
        );
        lv.setAdapter(theAdapter);

        //为按钮添加事件
        findViewById(R.id.btn_add_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nameTV = findViewById(R.id.edit_add_player_name);
                String playerName = nameTV.getText().toString();

                //构建键值对
                ContentValues values = new ContentValues();
                values.put(MyContract.PlayerEntry.COLUMN_NAME_PLAYER_NAME, playerName);
                values.put(MyContract.PlayerEntry.COLUMN_NAME_SEX, "Male");
                values.put(MyContract.PlayerEntry.COLUMN_NAME_AGE, "22");

                //插入新行（返回新行的自增字段值）
                long newRowId= db.insert(
                        MyContract.PlayerEntry.TABLE_NAME,
                        null,
                        values
                );
                //新值添加到集合中
                players.add(0, playerName);
                //通知适配器数据已改变
                theAdapter.notifyDataSetChanged();
            }
        });





    }
}
