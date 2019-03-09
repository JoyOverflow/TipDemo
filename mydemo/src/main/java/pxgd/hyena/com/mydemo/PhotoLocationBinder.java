package pxgd.hyena.com.mydemo;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

class PhotoLocationBinder implements SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (columnIndex == 1) {

            //图片经纬度
            double latitude = cursor.getDouble(columnIndex);
            double longitude = cursor.getDouble(columnIndex + 1);

            if (latitude == 0.0 && longitude == 0.0)
                ((TextView)view).setText("位置：未知");
            else
                ((TextView)view).setText("位置：" + latitude + ", " + longitude);

            //需返回true（否则SimpleCursorAdapter将会用默认方式绑定数据）
            return true;
        }
        else
            return false;
    }
}
