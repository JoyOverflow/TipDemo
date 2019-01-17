package pxgd.hyena.com.doudizhu.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import pxgd.hyena.com.doudizhu.R;
import pxgd.hyena.com.doudizhu.app.MyApplication;

public class DialogUtil {

    public static  void exitSystemDialog(Context mycontext){

        //创建对话框Builer对象
        AlertDialog.Builder builder=new AlertDialog.Builder(mycontext);
        //取消点击屏幕就返回
        builder.setCancelable(false);
        //显示对话框并返回对话框对象
        final AlertDialog dialog=builder.show();


        //加载对话框布局并设置为对话框界面
        View view= LayoutInflater.from(mycontext).inflate(R.layout.message_box_exit_game, null);
        dialog.getWindow().setContentView(view);
        //对话框按钮设定单击事件
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            //可播放音效并关闭对话框
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().play("ok.ogg");
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().play("SpecOk.ogg");
                dialog.dismiss();
                //退出游戏应用
                MyApplication.getInstance().exit();
            }
        });
    }

    /**
     * 退出游戏的对话框
     * @param mycontext
     */
    public static void exitGameDialog(Context mycontext) {

        //创建对话框Builer对象
        AlertDialog.Builder builder=new AlertDialog.Builder(mycontext);
        //取消点击屏幕、返回键关闭对话框
        builder.setCancelable(false);
        //显示对话框，并返回对话框对象
        final AlertDialog dialog=builder.show();
        //加载信息布局
        View view=LayoutInflater.from(mycontext).inflate(R.layout.message_box_exit_game, null);
        //设置对话框的界面
        dialog.getWindow().setContentView(view);
        //给对话框的界面取消按钮绑定单击事件
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //播放音效
                MyApplication.getInstance().play("ok.ogg");
                //关闭对话框
                dialog.dismiss();
            }
        });
        //给对话框的界面确定按钮绑定单击事件
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyApplication app=MyApplication.getInstance();
                //播放音效
                app.play("ok.ogg");
                //关闭对话框
                dialog.dismiss();
                //退出游戏
                app.getActivityList().get(app.getActivityList().size()-1).finish();
                //切换背景音乐
                app.playbgMusic("welcome.ogg");
            }
        });
    }
}
