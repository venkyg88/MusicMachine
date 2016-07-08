package assignment.venkat.com.musicmachine;

import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DownloadThread thread = new DownloadThread();
        thread.setName("downloadThread");
        thread.start();

        downloadButton = (Button) findViewById(R.id.downloadButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

                //send message to handler for processing.
                for(String song : Playlist.songs) {
                    Message message = Message.obtain();
                    message.obj = song;
                    thread.handler.sendMessage(message);
                }
            }
        });
    }


}
