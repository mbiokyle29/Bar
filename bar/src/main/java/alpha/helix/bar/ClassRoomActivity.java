package alpha.helix.bar;

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
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Handler;

public class ClassRoomActivity extends ActionBarActivity {

    private int lastSeekVal;
    private int consensusScore;
    private Firer fire;
    private ValueEventListener handler;
    private String eventCode;
    private ProgressBar progressBar;
    final String android_id =  "SECRT_KEY_1";//Secure.getString(getContentResolver(), Secure.ANDROID_ID).toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_room);

        final String code = getIntent().getStringExtra("code");

        fire = new Firer(code);

        TextView title = (TextView) findViewById(R.id.class_title);
        title.setText(code);

        // Set up progress bar
        // TODO get init from firebaseString code, String id
        progressBar = (ProgressBar) findViewById(R.id.class_start_bar);
        progressBar.setProgress(50);

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

    public HttpResponse postScore(int score, String id, String code) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.yoursite.com/");
        HttpResponse response = null;
        String scoreString = Integer.toString(score);

        try {
            // Execute HTTP Post Request
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("event", code));
            nameValuePairs.add(new BasicNameValuePair("uid", id));
            nameValuePairs.add(new BasicNameValuePair("score", scoreString));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return response;
    }
    public void updateProgressBar(String val)
    {
        Toast.makeText(ClassRoomActivity.this, "val", Toast.LENGTH_LONG ).show();
    }

}
