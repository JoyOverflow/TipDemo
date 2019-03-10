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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        //创建数据库对象并从数据库获取泛型数据集
        PlayerDbHelper helper = new PlayerDbHelper(AddPlayerActivity.this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        final List<String> players = helper.getAllPlayerName(db);

        //创建数组适配器对象（用数据库读取的泛型集合绑定它）
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AddPlayerActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                players
        );
        //查找列表视（为列表视设置数组适配器）
        ListView lv = findViewById(R.id.lst_player_view);
        lv.setAdapter(adapter);


        //为按钮添加事件
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //得到编辑框的值
                TextView nameTV = findViewById(R.id.edit_name);
                String playerName = nameTV.getText().toString();

                //构建键值对
                ContentValues values = new ContentValues();
                values.put(MyContract.PlayerEntry.COLUMN_PLAYER, playerName);
                values.put(MyContract.PlayerEntry.COLUMN_SEX, "Male");
                values.put(MyContract.PlayerEntry.COLUMN_AGE, "42");

                //插入新行（返回新行的自增字段值）
                long newId= db.insert(
                        MyContract.PlayerEntry.TABLE_NAME,
                        null,
                        values
                );
                //新值添加到集合中
                players.add(0, playerName);
                //调用适配器方法表示数据已改变（通过getView来刷新每Item的内容）
                adapter.notifyDataSetChanged();
            }
        });
    }
}
