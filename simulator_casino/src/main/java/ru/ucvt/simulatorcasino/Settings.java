package ru.ucvt.simulatorcasino;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.Locale;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener  {

    Context context;

    SharedPreferences language_pref;
    public static final String APP_LANGUAGE = "language";

    public SharedPreferences pref; //???

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);

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

        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Intent intent = getIntent();
        finish();
        startActivity(intent);

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
