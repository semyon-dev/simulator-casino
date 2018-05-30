package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class GameCards extends Activity {

    private EditText bet_edit;
    private TextView txt_balans;
    private ImageView pic1, pic2, pic3;
    private Button btn_play, btn_rules, max, min, x2, half;
    private Random random = new Random();
    private int n = 0;                  //n - счётчик для таймера
    private String s1, s2, s3, bet;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    private ScaleAnimation animation;

    private SetLanguage set_lang = new SetLanguage();
    private Balance balance = new Balance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        set_lang.set_language(getBaseContext());

        setContentView(R.layout.activity_game_cards);

        txt_balans = (TextView) findViewById(R.id.text_balans);
        txt_balans.setText(Integer.toString(balance.Get(getBaseContext())));

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);

        max = (Button) findViewById(R.id.max);
        min = (Button) findViewById(R.id.min);
        x2 = (Button) findViewById(R.id.x2);
        half = (Button) findViewById(R.id.half);

        btn_rules = (Button) findViewById(R.id.btn_rules);
        btn_play = (Button) findViewById(R.id.btn_play);
        bet_edit = (EditText) findViewById(R.id.bet_edit);

        btn_rules.setOnClickListener(btn_rules_Click);
        btn_play.setOnClickListener(btn_play_Click);

        max.setOnClickListener(bet_sum);
        min.setOnClickListener(bet_sum);
        x2.setOnClickListener(bet_sum);
        half.setOnClickListener(bet_sum);

        animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
    }

    View.OnClickListener bet_sum = new View.OnClickListener() {
        @Override
        public void onClick(View btn) {
            switch (btn.getId()) {
                case R.id.x2:
                    bet_edit.setText(Integer.toString(Integer.valueOf(String.valueOf(bet_edit.getText())) * 2));
                    break;
                case R.id.half:
                    bet_edit.setText(Integer.toString(Integer.valueOf(String.valueOf(bet_edit.getText())) / 2));
                    break;
                case R.id.max:
                    bet_edit.setText(Integer.toString(balance.Get(getBaseContext())));
                    break;
                case R.id.min:
                    bet_edit.setText("1");
                    break;
            }
        }
    };

    // создаем обработчик нажатия для кнопки играть
    View.OnClickListener btn_play_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (Integer.valueOf(String.valueOf(bet_edit.getText())) > balance.Get(getBaseContext())) {
                Toasty(getString(R.string.not_enough_money));
            } else {
                if (Integer.valueOf(String.valueOf(bet_edit.getText())) < 1) {
                    Toasty(getString(R.string.bet_very_low));
                } else {
                    btn_play.setEnabled(false);

                    bet = String.valueOf(bet_edit.getText());

                    mTimer = new Timer();
                    mMyTimerTask = new MyTimerTask();

                    // singleshot delay 1000 ms
                    mTimer.schedule(mMyTimerTask, 1000, 150);
                }
            }
        }
    };

    // создаем обработчик нажатия
    View.OnClickListener btn_rules_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameCards.this);  //выводим сообщение о выиграше
            mBuilder.setTitle(R.string.rules)
                    .setMessage(R.string.rules_game_cards)
                    .setCancelable(true)
                    .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();      //закрытие окна
                        }
                    });

            AlertDialog malert = mBuilder.create();
            malert.show();
        }
    };

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    n++;
                    if (n == 37) {                  // таймер выполняется 90 раз
                        n = 0;                      // сбрасываем счётчик
                        mTimer.cancel();            // прерываем таймер
                        compare();                  // метод сравнивания картинок
                        mTimer = null;
                        btn_play.setEnabled(true);
                    } else {

                        if (n < 15) {
                            s1 = "game1pic" + String.valueOf(1 + random.nextInt(3));
                            int res1 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic1.setImageResource(res1);
                            pic1.startAnimation(animation);
                        } else if (n == 15) {
                            pic1.clearAnimation();
                        }

                        if (n < 25) {
                            s2 = "game1pic" + String.valueOf(1 + random.nextInt(3));
                            int res2 = getResources().getIdentifier(s2, "drawable", getPackageName());
                            pic2.setImageResource(res2);
                            pic2.startAnimation(animation);
                        } else if (n == 25) {
                            pic2.clearAnimation();
                        }

                        if (n < 35) {
                            s3 = "game1pic" + String.valueOf(1 + random.nextInt(3));
                            int res3 = getResources().getIdentifier(s3, "drawable", getPackageName());
                            pic3.setImageResource(res3);
                            pic3.startAnimation(animation);
                        } else if (n == 35) {
                            pic3.clearAnimation();
                        }
                    }
                }
            });
        }

        void compare() {
            int sum;
            sum = Integer.valueOf(bet);

            if (s1.equals(s2) & s2.equals(s3)) {     //если все картинки одинаковые

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameCards.this);  //выводим сообщение о выиграше
                mBuilder.setTitle(R.string.hurray)
                        .setMessage(getString(R.string.win) + sum + " $")
                        .setCancelable(false)
                        .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();      //закрытие окна
                            }
                        });

                AlertDialog malert = mBuilder.create();
                malert.show();

                set_balance(sum * 5);    //в случае выиграша увеличиваем баланс на 500
            } else {
                set_balance(sum - sum * 2);
            }
        }
    }

    //метод изменения баланса
    private void set_balance(int sum) {
        balance.Update(sum, this);
        txt_balans.setText(Integer.toString(balance.Get(this)));
    }

    // при нажатии копки назад не даём выйти если игра не закончилась
    @Override
    public void onBackPressed() {
        if (mTimer != null) {
            Toasty(getString(R.string.wait_game));
        } else {
            Intent intent = new Intent(this, GameMenu.class);
            startActivity(intent);
        }
    }
    private void Toasty(String message){
        Toasty.warning(getBaseContext(), message, Toast.LENGTH_SHORT, true).show();
    }
}