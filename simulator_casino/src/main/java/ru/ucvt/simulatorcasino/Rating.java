package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.value.IntegerValue;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import es.dmoral.toasty.Toasty;

public class Rating extends Activity {

    private Balance balance = new Balance();

    private String nickname = null;
    private SharedPreferences nickname_pref;
    private int balanceInt = 0;

    private static final String NICKNAME = "nickname";
    private Language language = new Language();
    private Font font = new Font();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        final LinearLayout main = (LinearLayout) findViewById(R.id.layout);

        final LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        language.Set(getBaseContext());
        font.SetFont(getApplicationContext(), "SERIF", "fonts/lato_bold.ttf");
        font.SetSize(getResources().getConfiguration(), getBaseContext(), getResources());

        // connect to database
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("users").document("rating");

        nickname_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        nickname = nickname_pref.getString(NICKNAME, "noname");
        balanceInt = balance.Get(this);

        DocumentReference washingtonRef = db.collection("users").document("rating");

        washingtonRef
                .update(nickname, balanceInt)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error updating document", e);
                    }
                });

        DocumentReference docRef = db.collection("users").document("rating");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> players = new HashMap<>();
                        players = document.getData();

                        Object[] a = players.entrySet().toArray();
                        Arrays.sort(a, new Comparator() {
                            public int compare(Object o1, Object o2) {
                                return ((Map.Entry<String, Long>) o2).getValue()
                                        .compareTo(((Map.Entry<String, Long>) o1).getValue());
                            }
                        });

                        int i = 1;
                        for (Object e : a) {
                            String s =  ((Map.Entry<String, Long>) e).getKey() + " - " + ((Map.Entry<String, Long>) e).getValue();

                            TextView txt = new TextView(Rating.this);
                            txt.setText(Integer.toString(i) + ". " + s + " $");
                            txt.setTextSize(20);
                            txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            txt.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
                            txt.setTextColor(getResources().getColor(R.color.colorGreen));
                            txt.setLayoutParams(viewParams);
                            main.addView(txt);
                            i++;
                        }
                    } else {
                        //Log.d("TAG", "No such document");
                    }
                } else {
                    //Log.d("TAG","get failed with ", task.getException());
                }
            }
        });
    }
}
