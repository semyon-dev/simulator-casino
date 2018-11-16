package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import es.dmoral.toasty.Toasty;

public class MainMenu extends Activity {

    Font font = new Font();

    private Button btn_play, btn_settings, btn_rating;
    private Language language = new Language();
    private static long back_pressed;
    private FirebaseAnalytics mFirebaseAnalytics;
    String nickname;

    private SharedPreferences nickname_pref;
    private static final String NICKNAME = "nickname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language.Set(getBaseContext());

        font.SetFont(getApplicationContext(), "SERIF", "fonts/lato_bold.ttf");
        font.SetSize(getResources().getConfiguration(), getBaseContext(), getResources());

        nickname_pref = PreferenceManager.getDefaultSharedPreferences(this);
        nickname = nickname_pref.getString(NICKNAME, "null");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        if (nickname.equals("null")){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainMenu.this);  //выводим сообщение о выиграше
            mBuilder.setTitle("Welcome!")
                    .setMessage("Your should create nickname")
                    .setCancelable(false)
                    .setView(input)
                    .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                             nickname = String.valueOf(input.getText());

                             nickname_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                             SharedPreferences.Editor edit = nickname_pref.edit();
                             edit.putString(NICKNAME, nickname);
                             edit.apply(); // сохраняем изменения

                            dialogInterface.cancel();      //закрытие окна
                        }
                    });

            AlertDialog malert = mBuilder.create();
            malert.show();
        }


        setContentView(R.layout.activity_main_menu);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(btn_play_click);

        btn_rating = (Button)findViewById(R.id.btn_rating);
        btn_rating.setOnClickListener(btn_rating_click);

        btn_settings = (Button) findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(btn_settings_click);
    }

    // создаем обработчик нажатия для btn_play
    View.OnClickListener btn_play_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainMenu.this, GameMenu.class);
            startActivity(intent);
        }
    };


    // создаем обработчик нажатия для btn_play
    View.OnClickListener btn_rating_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainMenu.this, Rating.class);
            startActivity(intent);
        }
    };

    // создаем обработчик нажатия для btn_settings
    View.OnClickListener btn_settings_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainMenu.this, Settings.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        } else {
            Toasty.info(this, getString(R.string.press_again_exit)).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
