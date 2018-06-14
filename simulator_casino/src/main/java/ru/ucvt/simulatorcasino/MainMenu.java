package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import es.dmoral.toasty.Toasty;

public class MainMenu extends Activity {

    TypeFaceUtil typeFaceUtil = new TypeFaceUtil();

    private Button btn_play, btn_info, btn_settings;
    private Language language = new Language();
    private static long back_pressed;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language.Set(getBaseContext());

        typeFaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/lato_bold.ttf");

        setContentView(R.layout.activity_main_menu);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
                    .setMessage(getString(R.string.author) + "\n" + getString(R.string.version) + "\n" + getString(R.string.github))
                    // .setIcon(R.drawable.green)
                    .setCancelable(true)
                    .setNegativeButton("GitHub", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SemyonNovikov/SimulatorCasino"));
                            startActivity(browserIntent);
                        }
                    })
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
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
