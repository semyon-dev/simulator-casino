package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class Game777 extends Activity {

    private EditText bet_edit;
    private ImageView pic1, pic2, pic3, pic4, pic5;
    private Button btn_play, btn_rules, max, min, x2, half;
    private TextView txt_balance;
    private int n = 0, sum;         //n - счётчик для таймера
    private String s1, bet;

    private Timer mTimer;
    private Game777.MyTimerTask mMyTimerTask;

    private RotateAnimation rotate;

    private Game777_Logic MyGame777 = new Game777_Logic();
    private Language language = new Language();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language.Set(getBaseContext());

        setContentView(R.layout.activity_game_777);

        txt_balance = (TextView) findViewById(R.id.txt_balance);
        txt_balance.setText(Integer.toString(MyGame777.Get(getBaseContext())));

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);
        pic4 = (ImageView) findViewById(R.id.pic4);
        pic5 = (ImageView) findViewById(R.id.pic5);

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

        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(250);
        rotate.setRepeatCount(50);
    }

    // создаем обработчик нажатия
    View.OnClickListener btn_play_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if (Integer.valueOf(String.valueOf(bet_edit.getText())) > MyGame777.Get(getBaseContext())) {
                Toasty(getString(R.string.not_enough_money));
            } else {
                if (Integer.valueOf(String.valueOf(bet_edit.getText())) < 1) {
                    Toasty(getString(R.string.bet_very_low));
                } else {

                    btn_play.setEnabled(false);

                    bet = String.valueOf(bet_edit.getText());

                    pic1.startAnimation(rotate);
                    pic2.startAnimation(rotate);
                    pic3.startAnimation(rotate);
                    pic4.startAnimation(rotate);
                    pic5.startAnimation(rotate);

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

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    n++;
                    if (n == 52) {   // таймер выполняется 52 раза

                        n = 0;  // сбрасываем счётчик

                        // прерываем таймер
                        mTimer.cancel();

                        // метод сравнивания картинок
                        sum = MyGame777.Compare(Integer.valueOf(bet), getBaseContext());
                        txt_balance.setText(Integer.toString(MyGame777.Get(getBaseContext())));
                        if (sum > 0) {
                            dialog(sum);
                        }
                        mTimer = null;
                        btn_play.setEnabled(true);
                    } else {
                        if (n < 10) {
                            s1 = MyGame777.AddElements(0);
                            int res1 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic1.setImageResource(res1);
                        } else if (n == 10) {
                            pic1.clearAnimation();
                        }

                        if (n < 20) {
                            s1 = MyGame777.AddElements(1);
                            int res2 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic2.setImageResource(res2);
                        } else if (n == 20) {
                            pic2.clearAnimation();
                        }

                        if (n < 30) {
                            s1 = MyGame777.AddElements(2);
                            int res3 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic3.setImageResource(res3);
                        } else if (n == 30) {
                            pic3.clearAnimation();
                        }

                        if (n < 40) {
                            s1 = MyGame777.AddElements(3);
                            int res4 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic4.setImageResource(res4);
                        } else if (n == 40) {
                            pic4.clearAnimation();
                        }

                        if (n < 50) {
                            s1 = MyGame777.AddElements(4);
                            int res5 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic5.setImageResource(res5);
                        } else if (n == 50) {
                            pic5.clearAnimation();
                        }
                    }
                }
            });
        }
    }

    private void dialog(int sum) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Game777.this);  //выводим сообщение о выиграше
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
                    bet_edit.setText(Integer.toString(MyGame777.Get(getBaseContext())));
                    break;
                case R.id.min:
                    bet_edit.setText("1");
                    break;
            }
        }
    };

    // создаем обработчик нажатия
    View.OnClickListener btn_rules_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Game777.this);  //выводим сообщение о выиграше
            mBuilder.setTitle(R.string.rules)
                    .setMessage(R.string.rules_game_777)
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
