package pxgd.hyena.com.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pxgd.hyena.com.account.util.MyDbHelper;
import pxgd.hyena.com.account.util.MyProcessBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private final int DATE_MOTH_DAY = 1;
    private final int DATE_DAY = 2;
    private MyDbHelper db = null;
    private Calendar calendar = Calendar.getInstance();
    private String year = null;
    private String month = null;
    private String day = null;
    private String weekStart = null;
    private String weekEnd = null;
    private int income_amount = 0;
    private int expense_amount = 0;

    private Button add_expense_quickly_btn = null;
    private TextView month_tv = null;
    private TextView income_amount_tv = null;
    private TextView expense_amount_tv = null;
    private TextView budget_balance_amount_tv = null;
    private TextView date_of_month_tv = null;
    private TextView today_datestr_tv = null;
    private TextView today_expense_amount_tv = null;
    private TextView today_income_amount_tv = null;
    private TextView week_datestr_tv = null;
    private TextView week_expense_amount_tv = null;
    private TextView week_income_amount_tv = null;
    private TextView month_datestr_tv = null;
    private TextView month_expense_amount_tv = null;
    private TextView month_income_amount_tv = null;

    private MyProcessBar myProcessBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDate();
        loadingFormation();
        initInfo();
    }
    private void initDate(){
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        if(Integer.parseInt(month)<10)
            month ="0" + month;
        day = String.valueOf(calendar.get(Calendar.DATE));
        if(Integer.parseInt(day)<10)
            day ="0" + day;
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    }
    private void loadingFormation() {
        myProcessBar = (MyProcessBar) findViewById(R.id.budget_pb);

        add_expense_quickly_btn = (Button) findViewById(R.id.add_expense_quickly_btn);
        add_expense_quickly_btn.setOnClickListener(this);

        month_tv = (TextView) findViewById(R.id.month_tv);

        income_amount_tv = (TextView) findViewById(R.id.income_amount_tv);
        expense_amount_tv = (TextView) findViewById(R.id.expense_amount_tv);
        budget_balance_amount_tv = (TextView) findViewById(R.id.budget_balance_amount_tv);

        date_of_month_tv = (TextView) findViewById(R.id.date_of_month_tv);
        today_datestr_tv = (TextView) findViewById(R.id.today_datestr_tv);
        today_expense_amount_tv = (TextView) findViewById(R.id.today_expense_amount_tv);
        today_income_amount_tv = (TextView) findViewById(R.id.today_income_amount_tv);
        week_datestr_tv = (TextView) findViewById(R.id.week_datestr_tv);
        week_expense_amount_tv = (TextView) findViewById(R.id.week_expense_amount_tv);
        week_income_amount_tv = (TextView) findViewById(R.id.week_income_amount_tv);
        month_datestr_tv = (TextView) findViewById(R.id.month_datestr_tv);
        month_expense_amount_tv = (TextView) findViewById(R.id.month_expense_amount_tv);
        month_income_amount_tv = (TextView) findViewById(R.id.month_income_amount_tv);


        findViewById(R.id.today_row_rl).setOnClickListener(this);
        findViewById(R.id.week_row_rl ).setOnClickListener(this);
        findViewById(R.id.month_row_rl).setOnClickListener(this);
        findViewById(R.id.nav_account_btn).setOnClickListener(this);
        findViewById(R.id.nav_budget_btn).setOnClickListener(this);
    }

    private void initInfo() {
        db = SplashActivity.db;
        // text
//		calendar.set(2011, 0, 3);
//		db.insert("TBL_EXPENDITURE", new String[] {"AMOUNT","DATE"}, new String[] {"100",format(calendar.getTime())});
        month_tv.setText(month);

        Cursor cursor = db.rawQuery("select sum(AMOUNT) from TBL_INCOME",null);
        if (cursor.moveToNext()) {
            income_amount_tv.setText("¥ " + cursor.getDouble(0));
            income_amount = (int) cursor.getDouble(0);
        }

        cursor = db.rawQuery("select sum(AMOUNT) from TBL_EXPENDITURE",null);
        if (cursor.moveToNext()) {
            expense_amount = (int) cursor.getDouble(0);
            expense_amount_tv.setText("¥ " + cursor.getDouble(0));
        }

        cursor = db.rawQuery("select sum(BUDGET) from TBL_EXPENDITURE_CATEGORY",null);
        if (cursor.moveToNext()) {
            budget_balance_amount_tv.setText("-¥ " + cursor.getDouble(0));
        }
        date_of_month_tv.setText(day);
        today_datestr_tv.setText(year + "年" + month + "月" + day + "日");

        cursor = db.rawQuery("select sum(AMOUNT) from TBL_EXPENDITURE where strftime('%Y-%m-%d',DATE)=?", new String[]{format(calendar.getTime(),0)});
        if (cursor.moveToNext()) {
            today_expense_amount_tv.setText("- ¥ " + cursor.getDouble(0));
        }

        cursor = db.rawQuery("select sum(AMOUNT) from TBL_INCOME where strftime('%Y-%m-%d',DATE)=?", new String[]{format(calendar.getTime(),0)});
        if (cursor.moveToNext()) {
            today_income_amount_tv.setText("¥ " + cursor.getDouble(0));
        }


        cursor = db.rawQuery("select sum(AMOUNT) from TBL_EXPENDITURE where strftime('%Y-%m-%d',DATE)>=? and strftime('%Y-%m-%d',DATE)<=?", new String[]{getMondayOFWeek(),getCurrentWeekday()});
        if (cursor.moveToNext()) {
            week_expense_amount_tv.setText("- ¥ " + cursor.getDouble(0));
        }

        cursor = db.rawQuery("select sum(AMOUNT) from TBL_INCOME where strftime('%Y-%m-%d',DATE)>=? and strftime('%Y-%m-%d',DATE)<=?", new String[]{getMondayOFWeek(),getCurrentWeekday()});
        if (cursor.moveToNext()) {
            week_income_amount_tv.setText("¥ " + cursor.getDouble(0));
        }
        week_datestr_tv.setText(weekStart + "-" + weekEnd);


        month_datestr_tv.setText(month + "月01日-" + month + "月" + getDefaultDay() + "日");

        cursor = db.rawQuery("select sum(AMOUNT) from TBL_EXPENDITURE where strftime('%Y%m',DATE)=?", new String[]{year + month});
        if (cursor.moveToNext()) {
            month_expense_amount_tv.setText("- ¥ " + cursor.getDouble(0));
        }
        cursor = db.rawQuery("select sum(AMOUNT) from TBL_INCOME where strftime('%Y%m',DATE)=?", new String[]{year + month});
        if (cursor.moveToNext()) {
            month_income_amount_tv.setText("¥ " + cursor.getDouble(0));
        }
        cursor.close();
//		myProcessBar.setProgress(expense_amount/income_amount);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            initInfo();
        }
    }
    @Override
    public void onClick(View v) {
        Intent in = null;
        if (v == add_expense_quickly_btn) {
            in = new Intent(MainActivity.this, TransactActivity.class);
            startActivity(in);
            finish();
        }
        long curtime = getCurrentTime();
        switch (v.getId()) {
            case R.id.today_row_rl:
                showNavExpenseActivity(curtime, curtime, getString(R.string.text_title_today), ExpenseActivity.mode_day);
                break;
            case R.id.week_row_rl:
                showNavExpenseActivity(getFirstOfWeek(curtime), getLastOfWeek(curtime), getString(R.string.text_title_week), ExpenseActivity.mode_week);
                break;
            case R.id.month_row_rl:
                showNavExpenseActivity(getFirstOfMonth(curtime), getLastOfMonth(curtime),getString(R.string.text_title_month), ExpenseActivity.mode_month);
                break;
            case R.id.nav_account_btn:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.nav_budget_btn:
                startActivityForResult(new Intent(this, BudgetActivity.class),0);
                break;
        }
    }
    private void showNavExpenseActivity(long startTime, long endTime, String title, int mode)
    {
        Intent intent = new Intent(this, ExpenseActivity.class);
        intent.putExtra(ExpenseActivity.str_startTime, startTime);
        intent.putExtra(ExpenseActivity.str_endTime, endTime);
        intent.putExtra(ExpenseActivity.str_title, title);
        intent.putExtra(ExpenseActivity.str_mode, mode);
        startActivity(intent);
    }
    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }
    public static long getFirstOfWeek(long time) {
        Date date1, date2;
        date1 = new Date(time);
        date2 = new Date(date1.getYear(), date1.getMonth(), date1.getDate() - (date1.getDay() + 6) % 7);
        return date2.getTime();
    }
    public static long getLastOfWeek(long time) {
        Date date1, date2;
        date1 = new Date(time);
        date2 = new Date(date1.getYear(), date1.getMonth(), date1.getDate() - (date1.getDay() + 6) % 7 + 6);
        return date2.getTime();
    }
    public static long getFirstOfMonth(long time) {
        Date date1, date2;
        date1 = new Date(time);
        date2 = new Date(date1.getYear(), date1.getMonth(), 1);
        return date2.getTime();
    }
    public static long getLastOfMonth(long time) {
        Date date1, date2;
        date1 = new Date(time);
        date2 = new Date(date1.getYear(), date1.getMonth() + 1, 0);
        return date2.getTime();
    }


    private int getMondayPlus() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    public String getCurrentWeekday() {
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus+6);
        Date monday = currentDate.getTime();

        String preMonday = format(monday,0);
        weekEnd = format(monday,DATE_MOTH_DAY);
        return preMonday;
    }

    public String getMondayOFWeek(){
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        String preMonday = format(monday,0);
        weekStart = format(monday,DATE_MOTH_DAY);
        return preMonday;
    }


    public String getDefaultDay(){
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE,1);
        lastDate.add(Calendar.MONTH,1);
        lastDate.add(Calendar.DATE,-1);
        str=format(lastDate.getTime(),DATE_DAY);
        return str;
    }

    private String format(Date date,int id){
        String str = "";
        SimpleDateFormat ymd = null;
        switch (id) {
            case DATE_MOTH_DAY:
                ymd = new SimpleDateFormat("MM月dd日");
                break;
            case DATE_DAY:
                ymd = new SimpleDateFormat("dd");
                break;
            default:
                ymd = new SimpleDateFormat("yyyy-MM-dd");
                break;
        }
        str = ymd.format(date);
        return str;
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.point))
                .setMessage(getString(R.string.exit_message))
                .setPositiveButton(getString(R.string.exit_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        db.close();
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }).setNegativeButton(getString(R.string.exit_cancle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        }).show();
    }








}
