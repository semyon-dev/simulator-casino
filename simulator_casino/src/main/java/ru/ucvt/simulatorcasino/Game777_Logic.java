package ru.ucvt.simulatorcasino;

import java.util.Arrays;
import java.util.Random;


public class Game777_Logic {

    public int[] CardsArray = new int[5];

    int sum = 0;

    public String PicName;
    Random random = new Random();

    public String AddElements(int index) {
        int a = 1 + random.nextInt(5);
        PicName = "game2pic" + String.valueOf(a);
        CardsArray[index] = a;
        return PicName;
    }

   /* public int Compare1() {

        int max = 0;
        Arrays.sort(CardsArray); // сортируем массив

        for (int i = 0; i <= 5; i++) {
            for (int i2 = 1; i <= 4; i++) {
                if (CardsArray[i] == CardsArray[i2]) {
                    max++;
                }
            }
        }
  } */

public int Compare() {       // функция нахождения максимального кол-во повторяющихся картинок

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
        return sum; //возвращаем сумму выиграша
    }

}
