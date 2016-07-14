package assignment.venkat.com.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Comparator;

/**
 * Created by venkatgonuguntala on 7/8/16.
 */

public class DownloadHandler extends Handler {
    private static final String TAG = DownloadHandler.class.getSimpleName();
    private DownloadService mService;

    @Override
    public void handleMessage(Message msg) {
        downloadSong(msg.obj.toString());
        mService.stopSelf(msg.arg1);
    }

    private void downloadSong(String name) {
        //pretent to download!!!
        long endTime = System.currentTimeMillis() + 10 * 1000;
        while (System.currentTimeMillis() < endTime){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, name +" Downloaded");
    }

    public void setService(DownloadService service) {
        mService = service;
    }

}
