package assignment.venkat.com.musicmachine;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import assignment.venkat.com.musicmachine.model.Song;

/**
 * Created by venkatgonuguntala on 7/14/16.
 */

public class DownloadIntentService extends IntentService {

    private static final String TAG = DownloadIntentService.class.getSimpleName();

    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID = 22;

    public DownloadIntentService() {
        super("DownloadIntentService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Song song = intent.getParcelableExtra(MainActivity.EXTRA_SONG);

        Notification.Builder builder =  new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_favorite_heart)
                .setContentText(song.getTitle())
                .setContentTitle("Downloading")
                .setProgress(0, 0, true);

        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

        downloadSong(song.getTitle());
    }

    private void downloadSong(String name) {
        //pretent to download!!!
        long endTime = System.currentTimeMillis() + 3 * 1000;
        while (System.currentTimeMillis() < endTime){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, name +" Downloaded");

        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
