package ru.ucvt.simulatorcasino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game777 extends Activity {

    int sum;
    ImageView pic1,pic2,pic3,pic4,pic5;
    Button btn_play;
    TextView txt_balans;
    int balans, n = 0;           //n - счётчик для таймера
    String s1;

    private Timer mTimer;
    private Game777.MyTimerTask mMyTimerTask;

    ScaleAnimation animation;
    Game777_Logic MyGame777 = new Game777_Logic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_777);


        //MyGame777.CardsArray[];

        txt_balans = (TextView) findViewById(R.id.text_balans);  // инициализируем все элементы
        txt_balans.setText("3000");                              // начальный баланс

         pic1 = (ImageView) findViewById(R.id.pic1);
         pic2 = (ImageView) findViewById(R.id.pic2);
         pic3 = (ImageView) findViewById(R.id.pic3);
         pic4 = (ImageView) findViewById(R.id.pic4);
         pic5 = (ImageView) findViewById(R.id.pic5);

        btn_play = (Button) findViewById(R.id.button_play);
        btn_play.setOnClickListener(btn_play_Click);

        animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
       // animation.setRepeatMode(Animation.INFINITE);
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
                    if (n == 52) {                  // таймер выполняется 90 раз
                        n = 0;                      // сбрасываем счётчик
                        mTimer.cancel();            // прерываем таймер
                        sum = MyGame777.Compare();   // метод сравнивания картинок
                        balans_edit(sum);
                        if(sum>0){
                            dialog(sum);
                        }
                        btn_play.setEnabled(true);
                    } else {
                        animation.setDuration(n*10);

                        if (n < 10) {
                            s1 = MyGame777.AddElements(0);
                            int res1 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic1.setImageResource(res1);
                            pic1.startAnimation(animation);
                        }else if(n==10){
                           // pic1.clearAnimation();
                        }

                        if (n < 20) {
                            s1 = MyGame777.AddElements(1);
                            int res2 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic2.setImageResource(res2);
                            pic2.startAnimation(animation);
                        }else if(n==20){
                          //  pic2.clearAnimation();
                        }

                        if (n < 30) {
                            s1 = MyGame777.AddElements(2);
                            int res3 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic3.setImageResource(res3);
                            pic3.startAnimation(animation);
                        }else if(n==30){
                         //   pic3.clearAnimation();
                        }

                        if (n < 40) {
                            s1 = MyGame777.AddElements(3);
                            int res4 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic4.setImageResource(res4);
                            pic4.startAnimation(animation);
                        }else if(n==50){
                           // pic4.clearAnimation();
                        }

                        if (n < 50) {
                            s1 = MyGame777.AddElements(4);
                            int res5 = getResources().getIdentifier(s1, "drawable", getPackageName());
                            pic5.setImageResource(res5);
                            pic5.startAnimation(animation);
                        }else if(n==50){
                         //   pic5.clearAnimation();
                        }
                    }
                }
            });
        }
    }

    void dialog(int sum) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Game777.this);  //выводим сообщение о выиграше
        mBuilder.setTitle("Ура!")
                .setMessage("Вы выиграли " + sum)
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
}
