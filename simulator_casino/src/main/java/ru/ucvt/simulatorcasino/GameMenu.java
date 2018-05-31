package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameMenu extends Activity {

    Language language = new Language();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language.Set(getBaseContext());

        setContentView(R.layout.activity_game_menu);

        Button btn_game_cards = (Button) findViewById(R.id.btn_game_cards);
        Button btn_game_777 = (Button) findViewById(R.id.btn_game_777);
        Button btn_game_crypto = (Button) findViewById(R.id.btn_game_crypto);

        btn_game_cards.setOnClickListener(btn_game_Click);
        btn_game_777.setOnClickListener(btn_game_Click);
        btn_game_crypto.setOnClickListener(btn_game_Click);
    }

    View.OnClickListener btn_game_Click = new View.OnClickListener() {
        @Override
        public void onClick(View btn) {
            Intent intent = null;
            switch (btn.getId()) {
                case R.id.btn_game_cards:
                    intent = new Intent(GameMenu.this, GameCards.class);
                    break;
                case R.id.btn_game_777:
                    intent = new Intent(GameMenu.this, Game777.class);
                    break;
                case R.id.btn_game_crypto:
                    intent = new Intent(GameMenu.this, GameCrypto.class);
                    break;
            }
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
