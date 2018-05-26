package ru.ucvt.simulatorcasino;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

import java.util.Locale;

import static ru.ucvt.simulatorcasino.Settings.APP_LANGUAGE;

public class MainMenu extends Activity {

    ImageButton btn_play,btn_info,btn_settings;
    SharedPreferences language_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language_pref = PreferenceManager.getDefaultSharedPreferences(this);
        set_language(language_pref.getString(APP_LANGUAGE, "en"));

        setContentView(R.layout.activity_main_menu);

        btn_play = (ImageButton) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(btn_play_click);

        btn_info = (ImageButton) findViewById(R.id.btn_info);
        btn_info.setOnClickListener(btn_info_click);

        btn_settings = (ImageButton) findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(btn_settings_click);
    }

    void set_language(String languageToLoad){
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }


    // создаем обработчик нажатия для btn_play
    View.OnClickListener btn_play_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainMenu.this, GameMenu.class);
            startActivity(intent);
        }
    };

    // создаем обработчик нажатия для btn_info
    View.OnClickListener btn_info_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainMenu.this);
            mBuilder.setTitle(getString(R.string.about_game))
                    .setMessage(getString(R.string.author)  + "\n" + getString(R.string.version))
                    // .setIcon(R.drawable.green)
                    .setCancelable(true)
                    .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel(); //закрытие окна
                        }
                    });
            AlertDialog malert = mBuilder.create();
            malert.show();
        }
    };

    // создаем обработчик нажатия для btn_play
    View.OnClickListener btn_settings_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainMenu.this, Settings.class);
            startActivity(intent);
        }
    };
}
