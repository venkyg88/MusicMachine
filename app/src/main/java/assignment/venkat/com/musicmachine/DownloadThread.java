package assignment.venkat.com.musicmachine;

import android.os.Looper;
import android.util.Log;

/**
 * Created by venkatgonuguntala on 7/8/16.
 */

public class DownloadThread extends Thread {

    public static final String TAG = DownloadThread.class.getSimpleName();

    public DownloadHandler handler;
    @Override
    public void run() {
        Looper.prepare();
        handler = new DownloadHandler();
        Looper.loop();
    }

}
