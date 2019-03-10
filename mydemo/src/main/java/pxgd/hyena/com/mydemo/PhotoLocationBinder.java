package pxgd.hyena.com.mydemo;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

class PhotoLocationBinder implements SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

        //设置图片经纬度
        //if (columnIndex == 1) {
        if( view.getId()==R.id.item_value && columnIndex == 1 ){
            Log.d(PhotoActivity.TAG, "ViewBinder!");

            //得到游表数据的第1和2列
            double latitude = cursor.getDouble(columnIndex);
            double longitude = cursor.getDouble(columnIndex + 1);
            if (latitude == 0.0 && longitude == 0.0)
                ((TextView)view).setText("位置：未知");
            else
                ((TextView)view).setText("位置：" + latitude + ", " + longitude);

            //需返回true（否则SimpleCursorAdapter将会用默认方式绑定数据）
            //返回true（表示绑定已经完成，无需再调用系统默认绑定）
            return true;
        }
        else
            //调用系统默认绑定
            return false;
    }
}
