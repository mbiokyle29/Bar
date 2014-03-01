package alpha.helix.bar;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_class_room);

        final String code = getIntent().getStringExtra("code");

        TextView title = (TextView) findViewById(R.id.class_title);
        title.setText(code);

        lastSeekVal = 50;

        TextView counter = (TextView) findViewById(R.id.class_score_counter);
        counter.setText(Integer.toString(lastSeekVal));

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

    public void postScore(int score, String id, String code)
    {
        TextView counter = (TextView) findViewById(R.id.class_score_counter);
        counter.setText(Integer.toString(score));
        postScoreTask postScore = new postScoreTask();
        String[] params = new String[1];
        params[0] = "http://clickerfire.herokuapp.com/event?event="+code+"&uid="+id+"&score="+score;
        postScore.execute(params);
    }

    public void updateProgressBar(int value)
    {
        progressBar.setProgress(value);
    }

    protected class postScoreTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
			String url = params[0];
            String response = "";
			/* Download your data here. */
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            try
            {
                HttpResponse execute = client.execute(httpGet);
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null)
                {
                    response += s;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String response)
        {
        }
    }
}
