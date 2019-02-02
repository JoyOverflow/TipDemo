package yamba.marakana.com.yamba;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import winterwell.jtwitter.Twitter;

public class StatusActivity extends BaseActivity
        implements View.OnClickListener,TextWatcher {
    Button updateButton;
    EditText editText;
    TextView textCount;
    private static final String TAG = "StatusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //获取按钮的引用
        updateButton = (Button) findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(this);

        //文本编辑控件的引用
        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(this);

        //文本显示控件的引用
        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GREEN);
    }
    @Override
    public void onClick(View view){
        Log.d(TAG, "onClicked");
        String status = editText.getText().toString();
        new PostToTwitter().execute(status);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d(TAG, "beforeText");

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d(TAG, "onText");
    }
    @Override
    public void afterTextChanged(Editable editable) {
        Log.d(TAG, "afterText");
        int count = 140 - editable.length();
        textCount.setText(Integer.toString(count));
        textCount.setTextColor(Color.GREEN);
        if (count < 20)
            textCount.setTextColor(Color.YELLOW);
        if (count < 0)
            textCount.setTextColor(Color.RED);
    }

    //内部类
    class PostToTwitter extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                Twitter t=yamba.getTwitter();
                Twitter.Status status = t.updateStatus("succeed!!");
                return status.text;
            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to connect to twitter service", e);
                return "Failed to post";
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(StatusActivity.this, "ouyj:"+s, Toast.LENGTH_SHORT).show();
        }
    }

}
