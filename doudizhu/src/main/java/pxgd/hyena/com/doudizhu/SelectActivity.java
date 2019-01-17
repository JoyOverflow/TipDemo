package pxgd.hyena.com.doudizhu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pxgd.hyena.com.doudizhu.app.BaseActivity;
import pxgd.hyena.com.doudizhu.app.MyApplication;

public class SelectActivity extends BaseActivity implements View.OnClickListener {

    private MyApplication app;
    /**
     * 构造
     */
    public SelectActivity() {
        app= MyApplication.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        findViewById(R.id.choose_game_exit).setOnClickListener(this);
        findViewById(R.id.choose_game_btn_multi_play).setOnClickListener(this);
        findViewById(R.id.choose_game_btn_single_play).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        app.play("ok.ogg");
        switch (v.getId()) {
            case R.id.choose_game_exit:
                //关闭当前活动，自动返回上一活动
                this.finish();
                break;
            case R.id.choose_game_btn_multi_play:
                startActivity(new Intent(this, MultiGameActivity.class));
                break;
            case R.id.choose_game_btn_single_play:
                startActivity(new Intent(this, SingleGameActivity.class));
                break;
        }
    }
}
