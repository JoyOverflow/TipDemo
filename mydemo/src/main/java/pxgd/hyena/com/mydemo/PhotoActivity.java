package pxgd.hyena.com.mydemo;

import android.app.Dialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 使用v4版本的LoaderManager,SimpleCursorAdapter,CursorLoader
 */
public class PhotoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Bitmap bitmap = null;
    private byte[] mContent = null;

    //图片的描述
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE,
            MediaStore.Images.Media._ID
    };



    private ListView listView = null;
    private SimpleCursorAdapter simpleCursorAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        //查找到列表视
        listView = findViewById(R.id.photo_list_view);
        //创建游标适配器（使用自定义项布局视图）
        simpleCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.photo_list_item,
                null,
                STORE_IMAGES,
                new int[] { R.id.item_title, R.id.item_value},
                0
        );
        //为列表视设置适配器
        simpleCursorAdapter.setViewBinder(new PhotoLocationBinder());
        listView.setAdapter(simpleCursorAdapter);

        //加载数据，此处是getSupportLoaderManager（因它来自v4版本）
        getSupportLoaderManager().initLoader(0, null, this);
        //项点击事件处理
        listView.setOnItemClickListener(new PhotoOnClickListener());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(bitmap != null){
            bitmap.recycle();
        }
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(
                this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES,
                null,
                null,
                null);
        return cursorLoader;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }


    private class PhotoOnClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //创建对话框（使用布局文件）
            final Dialog dialog = new Dialog(PhotoActivity.this);
            dialog.setContentView(R.layout.photo_show);
            dialog.setTitle("显示图片");

            //查找按钮引用并设置事件（关闭对话框）
            Button btnClose =dialog.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    //释放图片资源
                    if(bitmap != null){
                        bitmap.recycle();
                    }
                }
            });


            Uri uri = MediaStore.Images.Media.
                    EXTERNAL_CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();

            //从Uri中读取图片
            ContentResolver resolver = getContentResolver();
            ImageView ivImageShow = dialog.findViewById(R.id.ivImageShow);
            try {
                mContent = readInputStream(resolver.openInputStream(Uri.parse(uri.toString())));
                bitmap = getBitmapFromBytes(mContent, null);

                //显示在图片视图中
                ivImageShow.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //显示对话框
            dialog.show();
        }

        public byte[] readInputStream(InputStream inStream) throws Exception {
            byte[] buffer = new byte[1024];
            int len;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            outStream.close();
            inStream.close();
            return data;
        }
        public Bitmap getBitmapFromBytes(byte[] bytes,BitmapFactory.Options opts){
            if (bytes != null){
                if (opts != null)
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
                else
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
            return null;
        }
    }

}
