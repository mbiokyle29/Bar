package alpha.helix.bar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by kyle on 3/1/14.
 */
public class Firer {
    private int lastScore;
    public Firer(String code)
    {
        // Create a reference to a Firebase location
        Firebase fire = new Firebase("https://resplendent-fire-2962.firebaseIO.com/class/"+code);
        ValueEventListener scoreListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.child("score").getValue().toString() != null)
            {
                updateProgressBar(Integer.getInteger(dataSnapshot.child("score").getValue().toString()));
            }
        }

        @Override
        public void onCancelled(FirebaseError error) {

        }
    };
    fire.addListenerForSingleValueEvent(scoreListener);
    }

    private void updateProgressBar(int score)
    {
        lastScore = score;
    }

    public int getScore()
    {
        return this.lastScore;
    }
}

