package pxgd.hyena.com.mydemo;

public class NDK {
    static {
        System.loadLibrary("main-lib");
    }
    public native String stringFromJNI();
}
