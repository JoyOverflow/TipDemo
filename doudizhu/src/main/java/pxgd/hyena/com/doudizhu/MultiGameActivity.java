package pxgd.hyena.com.doudizhu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;

import pxgd.hyena.com.doudizhu.app.BaseActivity;

public class MultiGameActivity extends BaseActivity implements View.OnClickListener {

    //帮助提示对话框
    private AlertDialog helpDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game);

        findViewById(R.id.multi_game_exit).setOnClickListener(this);
        findViewById(R.id.multi_game_help).setOnClickListener(this);
        findViewById(R.id.multi_game_btn_user_info).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.multi_game_exit:
                //退出
                this.finish();
                break;
            case R.id.multi_game_help:
                //帮助提示
                helpDialogShow();
                break;
            case R.id.multi_game_btn_user_info:
                //修改用户头像名称
                startActivityForResult(new Intent(this, PersonActivity.class),0x01);
                break;
        }
    }

    private void helpDialogShow(){
        //创建对话框
        helpDialog=new AlertDialog.Builder(this).create();
        //显示对话框
        helpDialog.show();
        //获得对话框窗口
        Window window=helpDialog.getWindow();
        //设置窗口的视图
        window.setContentView(R.layout.multigame_help);
    }
    //修改头像姓名返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }





}
