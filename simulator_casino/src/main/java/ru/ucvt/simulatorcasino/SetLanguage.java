package ru.ucvt.simulatorcasino;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

import static ru.ucvt.simulatorcasino.Settings.APP_LANGUAGE;

class SetLanguage {

    private SharedPreferences language_pref;
    private String languageToLoad;

    void set_language(Context context) {

        language_pref = PreferenceManager.getDefaultSharedPreferences(context);
        languageToLoad = language_pref.getString(APP_LANGUAGE, "ru");

        Configuration config = context.getResources().getConfiguration();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
