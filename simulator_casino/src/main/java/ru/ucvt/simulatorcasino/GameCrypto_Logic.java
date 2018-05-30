package ru.ucvt.simulatorcasino;

import android.content.Context;

import java.util.Arrays;
import java.util.Random;

class GameCrypto_Logic {

    private int[] array = new int[6];
    private Balance balance = new Balance();

    private String PicName;
    private Random random = new Random();

    String AddElements(int index) {
        int a = 1 + random.nextInt(6);
        PicName = "game3pic" + String.valueOf(a);
        array[index] = a;
        return PicName;
    }

    // функция нахождения максимального кол-во повторяющихся картинок
    int Compare(int bet, Context context) {

        Arrays.sort(array); // сортируем массив

        int m = 1, sum = 0, max=0;

        for (int i = 1; i < 6; i++) {
            if (array[i] == array[i - 1]) {
                m++;
            } else {
                if (m > max) {
                    max = m;
                    m = 1;
                }
            }
        }if (m>max) max = m;

        if (max == 6) {
            sum += bet * 20;
        } else if (max == 5) {
            sum += bet * 15;
        } else if (max == 4) {
            sum += bet * 7;
        } else if (max == 3) {
            sum += bet * 3;

            if(array[1] == array[3] && array[3] == array[5]){
                sum += bet*2;
            }
            if(array[0]==array[1] && array[1]==array[2] && array[3]==array[4] && array[4]==array[5])
            {
                sum += bet*7;
            }

        } else {
            sum = bet - (bet * 2);
        }

        balance.Update(sum, context); // обновляем баланс
        return sum; //возвращаем сумму выиграша
    }

    int Get(Context context) {
        return balance.Get(context);  // возвращаем баланс
    }
}
