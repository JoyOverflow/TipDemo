package pxgd.hyena.com.tipdemo;
import android.os.HandlerThread;
import android.util.Log;

/**
 * 图片下载类
 * @param <T>
 */
public class PhotoDownloader<T> extends HandlerThread {

    private static final String TAG = "PhotoDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;


    public PhotoDownloader(String name) {
        super(name);
    }

    public void queueThumbnail( T target,String url){
        Log.i(TAG,"URL:"+url);
    }
    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }
}
