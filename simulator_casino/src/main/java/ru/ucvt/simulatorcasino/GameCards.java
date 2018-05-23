package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameCards extends Activity {

    TextView txt_balans;
    ImageView pic1, pic2, pic3;
    Button btn_play;
    Random random = new Random();
    int balans, n = 0;                  //n - счётчик для таймера
    String s1, s2, s3;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    ScaleAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cards);

        txt_balans = (TextView) findViewById(R.id.text_balans);  // инициализируем все элементы
        txt_balans.setText("3000");                              // начальный баланс

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);

        btn_play = (Button) findViewById(R.id.button_play);
        btn_play.setOnClickListener(btn_play_Click);

        animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
    }

    // создаем обработчик нажатия
    View.OnClickListener btn_play_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            btn_play.setEnabled(false);

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
                    if (n == 37) {                  // таймер выполняется 90 раз
                        n = 0;                      // сбрасываем счётчик
                        mTimer.cancel();            // прерываем таймер
                        compare();                  // метод сравнивания картинок
                        btn_play.setEnabled(true);
                    } else {

                        if (n < 15) {
                            s1 = "game1pic" + String.valueOf(1 + random.nextInt(3));
                            int res1 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic1.setImageResource(res1);
                            pic1.startAnimation(animation);
                        }else if(n==15){
                            pic1.clearAnimation();
                        }

                        if (n < 25) {
                            s2 = "game1pic" + String.valueOf(1 + random.nextInt(3));
                            int res2 = getResources().getIdentifier(s2, "drawable", getPackageName());
                            pic2.setImageResource(res2);
                            pic2.startAnimation(animation);
                        }else if(n==25){
                            pic2.clearAnimation();
                        }

                        if (n < 35) {
                            s3 = "game1pic" + String.valueOf(1 + random.nextInt(3));
                            int res3 = getResources().getIdentifier(s3, "drawable", getPackageName());
                            pic3.setImageResource(res3);
                            pic3.startAnimation(animation);
                        }else if(n==35){
                            pic3.clearAnimation();
                        }
                    }
                }
            });
        }

        void compare() {
            if (s1.equals(s2) &  s2.equals(s3) ) {     //если все картинки одинаковые

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameCards.this);  //выводим сообщение о выиграше
                mBuilder.setTitle(getString(R.string.hurray))
                            .setMessage(getString(R.string.win) + " 500")
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

                balans_edit(500);    //в случае выиграша увеличиваем баланс на 500
            } else {
                balans_edit(-100);   //иначе уменьшаем на 100
            }
        }

        //метод изменения баланса
        void balans_edit(int edit) {
            balans = Integer.parseInt(String.valueOf(txt_balans.getText()));
            balans += edit;
            txt_balans.setText(Integer.toString(balans));
        }
    }
}
