package ru.ucvt.simulatorcasino;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends Activity {

    ImageButton btn_play,btn_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btn_play = (ImageButton) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(btn_play_click);

        btn_info = (ImageButton) findViewById(R.id.btn_info);
        btn_info.setOnClickListener(btn_info_click);
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
}
