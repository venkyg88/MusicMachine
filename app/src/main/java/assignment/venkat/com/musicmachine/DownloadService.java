package assignment.venkat.com.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by venkatgonuguntala on 7/8/16.
 */

public class DownloadService extends Service {

    public static final String TAG = DownloadService.class.getSimpleName();

    private DownloadHandler mDownloadHandler;
    @Override
    public void onCreate() {
        DownloadThread downloadThread = new DownloadThread();
        downloadThread.setName("DownloadedThread");
        downloadThread.start();
        while (mDownloadHandler == null) {

        }

        mDownloadHandler = downloadThread.handler;
        mDownloadHandler.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
        Message message = Message.obtain();
        message.obj = song;
        message.arg1 = startId;
        mDownloadHandler.sendMessage(message);

        return Service.START_REDELIVER_INTENT;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
