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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class Game777 extends Activity {

    int sum;
    ImageView pic1, pic2, pic3, pic4, pic5;
    Button btn_play;
    TextView txt_balans;
    int balans, n = 0;         //n - счётчик для таймера
    String s1;

    private Timer mTimer;
    private Game777.MyTimerTask mMyTimerTask;

    RotateAnimation rotate;

    Game777_Logic MyGame777 = new Game777_Logic();
    SetLanguage set_lang = new SetLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        set_lang.set_language(getBaseContext());

        setContentView(R.layout.activity_game_777);

        txt_balans = (TextView) findViewById(R.id.text_balans);  // инициализируем все элементы
        txt_balans.setText("3000");                              // начальный баланс

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);
        pic4 = (ImageView) findViewById(R.id.pic4);
        pic5 = (ImageView) findViewById(R.id.pic5);

        btn_play = (Button) findViewById(R.id.button_play);
        btn_play.setOnClickListener(btn_play_Click);

        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(250);
        rotate.setRepeatCount(50);
    }

    // создаем обработчик нажатия
    View.OnClickListener btn_play_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            btn_play.setEnabled(false);

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
    };

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    n++;
                    if (n == 52) {                   // таймер выполняется 90 раз
                        n = 0;                       // сбрасываем счётчик
                        mTimer.cancel();             // прерываем таймер
                        sum = MyGame777.Compare();   // метод сравнивания картинок
                        balans_edit(sum);
                        if (sum > 0) {
                            dialog(sum);
                        }
                        mTimer = null;
                        btn_play.setEnabled(true);
                    } else {
                        //animation.setDuration(n*10);

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

    void dialog(int sum) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Game777.this);  //выводим сообщение о выиграше
        mBuilder.setTitle(getString(R.string.hurray))
                .setMessage(getString(R.string.win) + " " + sum)
                // .setIcon(R.drawable.green)       //ПОСТАВИТЬ ИКОНКУ!! --------------------------
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

    // функция изменения баланса
    void balans_edit(int edit) {
        balans = Integer.parseInt(String.valueOf(txt_balans.getText()));
        balans += edit;
        txt_balans.setText(Integer.toString(balans));
    }

    @Override
    public void onBackPressed() {
        if (mTimer != null) {
            Toasty.warning(getBaseContext(), "Подождите пока закончится игра!", Toast.LENGTH_SHORT, true).show();
        } else {
            Intent intent = new Intent(this, GameMenu.class);
            startActivity(intent);
        }
    }
}
