package assignment.venkat.com.musicmachine;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;

import assignment.venkat.com.musicmachine.model.Song;

/**
 * Created by venkatgonuguntala on 7/14/16.
 */

public class PlayerService extends Service {

    private static final int REQUEST_CODE = 99;
    private MediaPlayer mMediaPlayer;
    //IBinder mIBinder = new LocalBinder();
    public Messenger mMessenger = new Messenger(new PlayerHandler(this));

    @Override
    public void onCreate() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }

    /*class LocalBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }*/



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = "";
        String artist = "";

        if (intent.getParcelableExtra(MainActivity.EXTRA_SONG) != null) {
            Song song = intent.getParcelableExtra(MainActivity.EXTRA_SONG);
            title = song.getTitle();
            artist = song.getArtist();
        }

        Intent customIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, customIntent, 0);

        Notification.Builder notificationBuiler = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(artist)
                .setContentIntent(pendingIntent);

        Notification notification = notificationBuiler.build();
        startForeground(11, notification);

        //we need to stop the service when song playing is done!
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
                stopForeground(true);
            }
        });
        return Service.START_NOT_STICKY;
    }

    //client methods
    public void play() {
        mMediaPlayer.start();
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public boolean isPlaying() {
        return  mMediaPlayer.isPlaying();
    }
}
