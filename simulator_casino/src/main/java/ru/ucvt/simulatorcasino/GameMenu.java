package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GameMenu extends Activity {

    SetLanguage set_lang = new SetLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_lang.set_language(getBaseContext());

        setContentView(R.layout.activity_game_menu);

        ImageButton btn_game_cards = (ImageButton) findViewById(R.id.btn_game_cards);
        ImageButton btn_game_777 = (ImageButton) findViewById(R.id.btn_game_777);

        btn_game_cards.setOnClickListener(btn_game_cards_Click);
        btn_game_777.setOnClickListener(btn_game_777_Click);
    }

    View.OnClickListener btn_game_cards_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GameMenu.this, GameCards.class);
            startActivity(intent);
        }
    };

    View.OnClickListener btn_game_777_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GameMenu.this, Game777.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
