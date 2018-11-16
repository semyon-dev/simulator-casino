package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.os.Bundle;

public class Rating extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //language.Set(getBaseContext());

        //font.SetFont(getApplicationContext(), "SERIF", "fonts/lato_bold.ttf");
        //font.SetSize(getResources().getConfiguration(), getBaseContext(), getResources());

        setContentView(R.layout.activity_rating);
    }
}
