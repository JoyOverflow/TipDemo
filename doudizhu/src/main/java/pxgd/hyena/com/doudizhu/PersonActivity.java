package pxgd.hyena.com.doudizhu;

import android.os.Bundle;
import android.view.View;

import pxgd.hyena.com.doudizhu.app.BaseActivity;

public class PersonActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        findViewById(R.id.person_info_exit).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_info_exit:
                saveAndClose();
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        saveAndClose();
    }
    private void saveAndClose(){
        setResult(0x02);
        this.finish();
    }

}
