package pxgd.hyena.com.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ExpenseActivity extends AppCompatActivity {

    public static String str_startTime 	= "startTime";
    public static String str_endTime	= "endTime";
    public static String str_title 		= "title";
    public static String str_mode 		= "mode";
    public static final int mode_none 	= 0;
    public static final int mode_month 	= 1;
    public static final int mode_week 	= 2;
    public static final int mode_day 	= 3;
    private static String datefmt = "yyyy年MM月dd日";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
    }
}
