package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import es.dmoral.toasty.Toasty;

public class MainMenu extends Activity {

    Font font = new Font();

    private Button btn_play, btn_settings;
    private Language language = new Language();
    private static long back_pressed;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language.Set(getBaseContext());

        font.ChangeFont(getApplicationContext(), "SERIF", "fonts/lato_bold.ttf");
        font.ChangeSize(getResources().getConfiguration(), getBaseContext(), getResources());

        setContentView(R.layout.activity_main_menu);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(btn_play_click);

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
