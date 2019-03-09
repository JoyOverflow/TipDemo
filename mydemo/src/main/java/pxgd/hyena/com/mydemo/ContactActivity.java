package pxgd.hyena.com.mydemo;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private TextView textResult;
    private Button btnLoadData;
    private boolean firstly=false;

    private String[] columns = {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        textResult = findViewById(R.id.txtResult);

        btnLoadData = findViewById(R.id.btnData);
        btnLoadData.setOnClickListener(this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new CursorLoader(
                    ContactActivity.this,
                    ContactsContract.Contacts.CONTENT_URI,
                    columns,
                    null,
                    null,
                    null
            );
        }
        return null;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0) {
            StringBuilder sb = new StringBuilder(128);
            //cursor.moveToFirst();
            while (data.moveToNext()) {
                sb.append(
                        data.getString(0) + " , " +
                        data.getString(1) + " , " +
                        data.getString(2) + "\n"
                );
            }
            textResult.setText(sb.toString());
        }
        else
            textResult.setText("No Contacts in device");

        data.close();
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    /**
     * 按钮事件处理
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnData:
                if(firstly==false){
                  getLoaderManager().initLoader(1, null, this);
                  firstly=true;
                }
                else
                  getLoaderManager().restartLoader(1,null,this);
                break;
            default:
                break;
        }
    }
}
