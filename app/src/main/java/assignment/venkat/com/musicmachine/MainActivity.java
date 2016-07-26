package assignment.venkat.com.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import assignment.venkat.com.musicmachine.adapter.PlaylistAdapter;
import assignment.venkat.com.musicmachine.model.Song;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_SONG = "EXTRA_SONG";
    public static final int REQUEST_FAVORITE = 0; //it could be any integer
    public static final String EXTRA_FAVORITE = "EXTRA_FAVORITE";
    public static final String EXTRA_LIST_POSITION = "EXTRA_LIST_POSITION";

    private Button downloadButton;
    private Button playButton;
    private Messenger mMessengerService;
    private Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));
    private boolean mBound = false;

    private PlaylistAdapter mAdapter;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mBound = true;
            mMessengerService = new Messenger(binder);
            Message message = Message.obtain();
            message.arg1 = 2;
            message.arg2 = 1;
            message.replyTo = mActivityMessenger;
            try {
                mMessengerService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public void changePlayButtonText(String text) {
        playButton.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadButton = (Button) findViewById(R.id.downloadButton);
        playButton = (Button) findViewById(R.id.playButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //downloadSongs();
                testIntents();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mBound){
                    Intent intent = new Intent(MainActivity.this, PlayerService.class);
                    startService(intent);
                    Message message = Message.obtain();
                    message.arg1 = 2;
                    message.replyTo = mActivityMessenger;
                    try {
                        mMessengerService.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new PlaylistAdapter(this, Playlist.songs);
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void testIntents() {
        //Explicit intents
        /*Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(EXTRA_SONG, "Gradle, Gradle, Gradle!");
        startActivityForResult(intent, REQUEST_FAVORITE);*/

        //Implicit intents
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri geoLocation = Uri.parse("geo:0,0?q=40.745102, -74.155483(Venkathouse)");
        intent.setData(geoLocation);
        startActivity(intent);
    }

    private void downloadSongs() {
        Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

        //send message to handler for processing.
        for(Song song : Playlist.songs) {
            Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
            intent.putExtra(EXTRA_SONG, song);
            startService(intent);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent= new Intent(this, PlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, PlayerService.class);
        if(mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FAVORITE) {
            if (resultCode == RESULT_OK) {
                boolean result = data.getBooleanExtra(EXTRA_FAVORITE, false);
                Log.i(TAG, "Is Favorite " +result);
                int position = data.getIntExtra(EXTRA_LIST_POSITION , 0);
                Playlist.songs[position].setIsFavorite(result);
                mAdapter.notifyItemChanged(position);
            }
        }
    }
}
