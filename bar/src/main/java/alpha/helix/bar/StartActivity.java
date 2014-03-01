package alpha.helix.bar;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends ActionBarActivity {
   final String android_id =  "SECRET_KEY_1";//Secure.getString(getContentResolver(), Secure.ANDROID_ID).toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
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

    public void onTokenButtonClick(View v)
    {
        //TODO validate token
        EditText token = (EditText) findViewById(R.id.start_token_edit);
        String eventCode = token.getText().toString();
        if(eventCode != null)
        {
            Intent goToClass = new Intent(this, ClassRoomActivity.class);
            goToClass.putExtra("code", eventCode);
            startActivity(goToClass);
            // TODO postSession(eventCode,android_id);
        } else {
            Toast.makeText(this, "Please Enter A code", Toast.LENGTH_LONG ).show();
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        } else {
            return false;
        }
    }
}
