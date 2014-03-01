package alpha.helix.bar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ClassRoomActivity extends ActionBarActivity {

    private int lastSeekVal;
    private int consensusScore;
    private Firebase fire;
    private ValueEventListener handler;
    private String eventCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up progress bar
        // TODO get init from firebase
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.class_start_bar);
        progressBar.setProgress(50);

        // set up seekbar
        SeekBar seek = (SeekBar) findViewById(R.id.class_seek_bar);
        lastSeekVal = 50;
        seek.setProgress(lastSeekVal);

        setContentView(R.layout.activity_class_room);


        // Create a reference to a Firebase location
        fire = new Firebase("https://myapp.firebaseIO-demo.com/");

        // Read data and react to changes
        handler = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot arg0) {
            }
            @Override
            public void onCancelled(FirebaseError error) {
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.class_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSeekBarClick()
    {
        SeekBar seek = (SeekBar) findViewById(R.id.class_seek_bar);
        int score = seek.getProgress();
        if(score != lastSeekVal)
        {
            //TODO CALL FIRE
        }
    }
}
