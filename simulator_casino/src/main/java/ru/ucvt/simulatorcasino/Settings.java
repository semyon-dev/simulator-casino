package ru.ucvt.simulatorcasino;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import es.dmoral.toasty.Toasty;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener  {

    Context context;
    private SharedPreferences language_pref;
    static final String APP_LANGUAGE = "language";

    Preference btn_new_balance;
    SharedPreferences pref;
    Balance balance = new Balance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);

        btn_new_balance = findPreference("btn_new_balance");

        btn_new_balance.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (balance.Get(Settings.this)==0){
                    balance.Update(100,Settings.this);
                    Toasty.success(Settings.this,getString(R.string.new_money)).show();
                }else {
                    Toasty.error(Settings.this,getString(R.string.balance_more_than_zero)).show();
                }
                return true;
            }
        });

        context = getApplicationContext();
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    // функция изменения и сохранения настроек языка
    void set_language(String languageToLoad){

        language_pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = language_pref.edit();
        edit.putString(APP_LANGUAGE, languageToLoad);
        edit.commit();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit_pref = pref.edit();
        edit_pref.putString(APP_LANGUAGE, languageToLoad);
        edit_pref.commit();

        finish();  // завершаем текущее активити

        // переходим в главное меню
        Intent intent_main = new Intent(Settings.this, MainMenu.class);
        startActivity(intent_main);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (pref.getString("language","English").contains("English")){
            set_language("en");
        }
        else if (pref.getString("language","English").contains("Русский")){
            set_language("ru");
        }
    }
}
