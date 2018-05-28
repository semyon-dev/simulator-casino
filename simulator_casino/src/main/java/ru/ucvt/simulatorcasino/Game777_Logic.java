package ru.ucvt.simulatorcasino;

import android.content.Context;

import java.util.Arrays;
import java.util.Random;

class Game777_Logic {

    private int[] CardsArray = new int[5];
    private Balance balance = new Balance();

    private int sum = 0;

    private String PicName;
    private Random random = new Random();

    String AddElements(int index) {
        int a = 1 + random.nextInt(5);
        PicName = "game2pic" + String.valueOf(a);
        CardsArray[index] = a;
        return PicName;
    }

    int Compare(Context context) {       // функция нахождения максимального кол-во повторяющихся картинок

    Arrays.sort(CardsArray); // сортируем массив
    int m = 1;
    int max = 0;

    for (int i = 1; i < 5; i++) {
        if (CardsArray[i] == CardsArray[i - 1]) {
            m++;
        } else {
            if (m > max) {
                max = m;
                m = 1;
            }
        }
    }
    if (m>max) max = m;

        if (max == 5) {
            sum = 1500;
        } else if (max == 4) {
            sum = 1000;
        } else if (max == 3) {
            sum = 300;
        } else if (max == 2) {
            sum = -100;
        } else {
            sum = -100;
        }
        balance.Update(sum, context);
        return sum; //возвращаем сумму выиграша
    }

    int Get(Context context) {
        return balance.Get(context);
    }

}
