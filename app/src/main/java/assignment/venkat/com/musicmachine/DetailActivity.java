package assignment.venkat.com.musicmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import assignment.venkat.com.musicmachine.model.Song;

/**
 * Created by venkatgonuguntala on 7/25/16.
 */

public class DetailActivity extends AppCompatActivity {

    private Song mSong;
    public static final String SHARE_SONG = "assignment.venkat.intent.action.SHARE_SONG";

    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleLabel = (TextView) findViewById(R.id.songTitleLabel);
        final CheckBox favoriteCheckbox = (CheckBox) findViewById(R.id.checkBox);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        Intent intent = getIntent();
        /*if (intent.getStringExtra(MainActivity.EXTRA_SONG) != null) {
            String songTitle = intent.getStringExtra(MainActivity.EXTRA_SONG);
            titleLabel.setText(songTitle);
        }*/

        if (Intent.ACTION_SEND.equals(intent.getAction())){
            handleSendIntent(intent);

        } else {
            if (intent.getParcelableExtra(MainActivity.EXTRA_SONG) != null) {
                mSong = intent.getParcelableExtra(MainActivity.EXTRA_SONG);
                titleLabel.setText(mSong.getTitle());
                favoriteCheckbox.setChecked(mSong.isFavorite());
            }

            final int listPosition = intent.getIntExtra(MainActivity.EXTRA_LIST_POSITION, 0);

            favoriteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.EXTRA_FAVORITE, isChecked);
                    resultIntent.putExtra(MainActivity.EXTRA_LIST_POSITION, listPosition);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
        }
    }

    private void handleSendIntent(Intent intent) {
        if (intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
            Snackbar.make(mRelativeLayout, intent.getStringExtra(Intent.EXTRA_TEXT),
                                Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_share) {
            if (mSong != null) {
                Intent customIntent = new Intent(SHARE_SONG);
                customIntent.putExtra(MainActivity.EXTRA_SONG, mSong);
                /*Intent chooser = Intent.createChooser(customIntent, "share song");
                startActivity(chooser);*/

                //WE could very easily use broadcast intent receiver getting rid of chooser
                // and sending the intent with sendbroadcast than startActivity
                sendBroadcast(customIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}