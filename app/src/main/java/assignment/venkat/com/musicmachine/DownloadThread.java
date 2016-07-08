package assignment.venkat.com.musicmachine;

import android.util.Log;

/**
 * Created by venkatgonuguntala on 7/8/16.
 */

public class DownloadThread extends Thread {

    public static final String TAG = DownloadThread.class.getSimpleName();
    @Override
    public void run() {
        downloadSong();
    }

    private void downloadSong() {
        //pretent to download!!!
        long endTime = System.currentTimeMillis() + 10 * 1000;
        while (System.currentTimeMillis() < endTime){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Song Downloaded");
    }

}
