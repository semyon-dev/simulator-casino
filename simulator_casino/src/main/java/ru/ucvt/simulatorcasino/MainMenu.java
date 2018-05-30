package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    Button btn_play,btn_info,btn_settings;
    SetLanguage set_lang = new SetLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        set_lang.set_language(getBaseContext());

        setContentView(R.layout.activity_main_menu);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(btn_play_click);

        btn_info = (Button) findViewById(R.id.btn_info);
        btn_info.setOnClickListener(btn_info_click);

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

    // создаем обработчик нажатия для btn_info
    View.OnClickListener btn_info_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainMenu.this);
            mBuilder.setTitle(getString(R.string.about_game))
                    .setMessage(getString(R.string.author)  + "\n" + getString(R.string.version) + "\n" + getString(R.string.github))
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

    // создаем обработчик нажатия для btn_settings
    View.OnClickListener btn_settings_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainMenu.this, Settings.class);
            startActivity(intent);
        }
    };

    // при нажатии кнопки назад перезагружаем активити
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
