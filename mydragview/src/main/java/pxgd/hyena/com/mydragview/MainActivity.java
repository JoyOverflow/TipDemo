package pxgd.hyena.com.mydragview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SonnyJackDragView mSonnyJackDragView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn_move);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean needNearEdge = mSonnyJackDragView.getNeedNearEdge();
                mSonnyJackDragView.setNeedNearEdge(!needNearEdge);
                if (needNearEdge)
                    mButton.setText("移至边沿");
                else
                    mButton.setText("固定位置");
            }
        });



        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了...", Toast.LENGTH_SHORT).show();
            }
        });


        mSonnyJackDragView = new SonnyJackDragView.Builder()
                .setActivity(this)
                .setDefaultLeft(30)
                .setDefaultTop(30)
                .setNeedNearEdge(false)
                .setSize(100)
                .setView(imageView)
                .build();
    }
}
