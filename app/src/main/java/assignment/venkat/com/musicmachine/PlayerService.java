package assignment.venkat.com.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by venkatgonuguntala on 7/14/16.
 */

public class PlayerService extends Service {

    private MediaPlayer mMediaPlayer;
    IBinder mIBinder = new LocalBinder();

    @Override
    public void onCreate() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
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

    class LocalBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
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
