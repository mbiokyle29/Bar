package alpha.helix.bar;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Handler;

public class ClassRoomActivity extends ActionBarActivity {

    private int lastSeekVal;
    private int consensusScore;
    public Firebase fire;
    private ValueEventListener handler;
    private String eventCode;
    private ProgressBar progressBar;
    final String android_id =  "SECRT_KEY_1";//Secure.getString(getContentResolver(), Secure.ANDROID_ID).toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_room);

        final String code = getIntent().getStringExtra("code");

        TextView title = (TextView) findViewById(R.id.class_title);
        title.setText(code);

        // Set up progress bar
        progressBar = (ProgressBar) findViewById(R.id.class_start_bar);

        fire = new Firebase("https://blistering-fire-5490.firebaseIO.com/class/"+code+"/score");
        fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateProgressBar(dataSnapshot.getValue(int.class));
            }

            @Override
            public void onCancelled(FirebaseError error) {

            }
        });

        // set up seekbar
        SeekBar seek = (SeekBar) findViewById(R.id.class_seek_bar);
        lastSeekVal = 50;
        seek.setProgress(lastSeekVal);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub
                int val = arg0.getProgress();
                if(val != lastSeekVal)
                {
                    postScore(val, android_id, code);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

        });
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

    public void postScore(int score, String id, String code) {
        Toast.makeText(ClassRoomActivity.this, score, Toast.LENGTH_LONG).show();
    }

    public void updateProgressBar(int value)
    {
        progressBar.setProgress(value);
    }
}
