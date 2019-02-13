package pxgd.hyena.com.mydemo_2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication app=(MyApplication)getApplication();
        app.twitter();

        getWindow().setStatusBarColor(Color.RED);
        getWindow().setNavigationBarColor(Color.YELLOW);

        startService(new Intent(this, UpdaterService.class));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, UpdaterService.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
