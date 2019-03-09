package pxgd.hyena.com.mydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置按钮事件处理
        findViewById(R.id.btnAddPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        AddPlayerActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnShowPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        ShowPlayerActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnShowPhotos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        PhotoActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnShowContacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        ContactActivity.class
                );
                startActivity(intent);
            }
        });
        findViewById(R.id.btnOperation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        OperationActivity.class
                );
                startActivity(intent);
            }
        });


    }
}
