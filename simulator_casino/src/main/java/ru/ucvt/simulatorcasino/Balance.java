package ru.ucvt.simulatorcasino;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class Balance {

    private SharedPreferences balance_pref;
    private static final String APP_BALANCE = "balance";
    private int balance;

    // обновляем баланс и сразу сохраняем его
    void Update(int sum, Context context) {
        balance = Get(context);
        balance += sum;
        balance_pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = balance_pref.edit();
        edit.putInt(APP_BALANCE, balance);
        edit.apply(); // сохраняем изменения
    }

    // получаем баланс
    int Get(Context context) {
        balance_pref = PreferenceManager.getDefaultSharedPreferences(context);
        balance = balance_pref.getInt(APP_BALANCE, 100);
        return balance;
    }
}
