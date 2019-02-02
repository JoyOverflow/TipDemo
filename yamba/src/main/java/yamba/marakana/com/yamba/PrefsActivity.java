package yamba.marakana.com.yamba;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by oupxgd on 2017/10/24.
 */

public class PrefsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
