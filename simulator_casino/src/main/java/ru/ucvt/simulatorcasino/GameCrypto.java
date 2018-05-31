package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class GameCrypto extends Activity {

    private Language language = new Language();
    private Button btn_play, btn_rules, max, min, x2, half;
    private ImageView pic1, pic2, pic3, pic4, pic5, pic6;
    private TextView txt_balance;
    private EditText bet_edit;
    private Timer mTimer;
    private GameCrypto.MyTimerTask mMyTimerTask;

    private int n = 0, sum;
    private String s1, bet;
    private GameCrypto_Logic gameCrypto = new GameCrypto_Logic();

    AnimationSet set;
    Animation animation;
    Animation animOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language.Set(this);
        setContentView(R.layout.activity_game_crypto);

        txt_balance = (TextView) findViewById(R.id.text_balans);  // инициализируем все элементы
        txt_balance.setText(Integer.toString(gameCrypto.Get(getBaseContext())));

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);
        pic4 = (ImageView) findViewById(R.id.pic4);
        pic5 = (ImageView) findViewById(R.id.pic5);
        pic6 = (ImageView) findViewById(R.id.pic6);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(btn_play_Click);

        btn_rules = (Button) findViewById(R.id.btn_rules);
        bet_edit = (EditText) findViewById(R.id.bet_edit);

        max = (Button) findViewById(R.id.max);
        min = (Button) findViewById(R.id.min);
        x2 = (Button) findViewById(R.id.x2);
        half = (Button) findViewById(R.id.half);

        max.setOnClickListener(bet_sum);
        min.setOnClickListener(bet_sum);
        x2.setOnClickListener(bet_sum);
        half.setOnClickListener(bet_sum);
        btn_rules.setOnClickListener(btn_rules_Click);

        //rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //  rotate.setDuration(250);
        // rotate.setRepeatCount(50);

        // animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f,
        // ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //   animation.setDuration(100);
        //   animation.setRepeatCount(50);

        animation = AnimationUtils.makeInAnimation(this, true);
        animation.setStartOffset(1000);
        animation.setRepeatCount(100);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setDuration(500);
        animOut = AnimationUtils.makeOutAnimation(this, true);
        animOut.setRepeatMode(Animation.INFINITE);
        animOut.setStartOffset(1000);
        animOut.setRepeatCount(100);
        animOut.setDuration(500); // спустя 3 секунды TextView покинет экран
        set = new AnimationSet(true);
        set.addAnimation(animation);
        set.addAnimation(animOut);
        set.setRepeatCount(100);
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
                    bet_edit.setText(Integer.toString(gameCrypto.Get(getBaseContext())));
                    break;
                case R.id.min:
                    bet_edit.setText("1");
                    break;
            }
        }
    };

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    n++;
                    if (n == 62) {   // таймер выполняется 52 раза

                        n = 0;  // сбрасываем счётчик

                        // прерываем таймер
                        mTimer.cancel();

                        // метод сравнивания картинок
                        sum = gameCrypto.Compare(Integer.valueOf(bet), getBaseContext());
                        txt_balance.setText(Integer.toString(gameCrypto.Get(getBaseContext())));
                        if (sum > 0) {
                            dialog(sum);
                        }
                        mTimer = null;
                        btn_play.setEnabled(true);
                    } else {
                        if (n < 10) {
                            s1 = gameCrypto.AddElements(0);
                            int res1 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic1.setImageResource(res1);
                            pic1.startAnimation(set);
                        } else if (n == 10) {
                            pic1.clearAnimation();
                        }

                        if (n < 20) {
                            s1 = gameCrypto.AddElements(1);
                            int res2 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic2.setImageResource(res2);
                            pic2.startAnimation(set);
                        } else if (n == 20) {
                            pic2.clearAnimation();
                        }

                        if (n < 30) {
                            s1 = gameCrypto.AddElements(2);
                            int res3 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic3.setImageResource(res3);
                            pic3.startAnimation(set);
                        } else if (n == 30) {
                            pic3.clearAnimation();
                        }

                        if (n < 40) {
                            s1 = gameCrypto.AddElements(3);
                            int res4 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic4.setImageResource(res4);
                            pic4.startAnimation(set);
                        } else if (n == 40) {
                            pic4.clearAnimation();
                        }

                        if (n < 50) {
                            s1 = gameCrypto.AddElements(4);
                            int res5 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic5.setImageResource(res5);
                            pic5.startAnimation(set);
                        } else if (n == 50) {
                            pic5.clearAnimation();
                        }
                        if (n < 60) {
                            s1 = gameCrypto.AddElements(5);
                            int res6 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic6.setImageResource(res6);
                            pic6.startAnimation(set);
                        } else if (n == 60) {
                            pic6.clearAnimation();
                        }
                    }
                }
            });
        }
    }

    // создаем обработчик нажатия
    View.OnClickListener btn_play_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (Integer.valueOf(String.valueOf(bet_edit.getText())) > gameCrypto.Get(getBaseContext())) {
               Toasty(getString(R.string.not_enough_money));
            } else {
                if (Integer.valueOf(String.valueOf(bet_edit.getText())) < 1) {
                   Toasty(getString(R.string.bet_very_low));
                } else {

                    btn_play.setEnabled(false);

                    bet = String.valueOf(bet_edit.getText());

                    pic1.startAnimation(set);
                    pic2.startAnimation(set);
                    pic3.startAnimation(set);
                    pic4.startAnimation(set);
                    pic5.startAnimation(set);
                    pic6.startAnimation(set);

                    if (mTimer != null) {
                        mTimer.cancel();
                    }
                    mTimer = new Timer();
                    mMyTimerTask = new MyTimerTask();

                    // singleshot delay 1000 ms
                    mTimer.schedule(mMyTimerTask, 1000, 150);
                }
            }
        }
    };

    private void dialog(int sum) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameCrypto.this);  //выводим сообщение о выиграше
        mBuilder.setTitle(getString(R.string.hurray))
                .setMessage(getString(R.string.win) + " " + sum + " $")
                .setCancelable(false)
                .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();      //закрытие окна
                    }
                });

        AlertDialog malert = mBuilder.create();
        malert.show();
    }

    // нажатие на кнопку назад
    @Override
    public void onBackPressed() {
        if (mTimer != null) {
            Toasty(getString(R.string.wait_game));
        } else {
            Intent intent = new Intent(this, GameMenu.class);
            startActivity(intent);
        }
    }

    // создаем обработчик нажатия
    View.OnClickListener btn_rules_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameCrypto.this);  //выводим сообщение о правилах
            mBuilder.setTitle(getString(R.string.rules))
                    .setMessage(getString(R.string.rules_game_crypto))
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

    private void Toasty(String message){
        Toasty.warning(getBaseContext(), message, Toast.LENGTH_SHORT, true).show();
    }
}