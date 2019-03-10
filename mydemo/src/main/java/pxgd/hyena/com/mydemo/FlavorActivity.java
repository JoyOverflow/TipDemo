package pxgd.hyena.com.mydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Arrays;

public class FlavorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavor);

        //对象数组
        Flavor[] androidFlavors = {
                new Flavor("Cupcake", "1.5", R.drawable.cupcake),
                new Flavor("Donut", "1.6", R.drawable.donut),
                new Flavor("Eclair", "2.0-2.1", R.drawable.eclair),
                new Flavor("Froyo", "2.2-2.2.3", R.drawable.froyo),
                new Flavor("GingerBread", "2.3-2.3.7", R.drawable.gingerbread),
                new Flavor("Honeycomb", "3.0-3.2.6", R.drawable.honeycomb),
                new Flavor("Ice Cream Sandwich", "4.0-4.0.4", R.drawable.icecream),
                new Flavor("Jelly Bean", "4.1-4.3.1", R.drawable.jellybean),
                new Flavor("KitKat", "4.4-4.4.4", R.drawable.kitkat),
                new Flavor("Lollipop", "5.0-5.1.1", R.drawable.lollipop)
        };

        //用对象数组创建（自定义）数组适配器
        FlavorAdapter flavorAdapter;
        flavorAdapter = new FlavorAdapter(this, Arrays.asList(androidFlavors));

        //列表视设置适配器
        ListView listView = findViewById(R.id.lst_flavor);
        listView.setAdapter(flavorAdapter);
    }
}
