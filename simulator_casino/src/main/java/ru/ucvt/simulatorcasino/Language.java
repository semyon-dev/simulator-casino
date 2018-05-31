package ru.ucvt.simulatorcasino;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

class Language {

    private static final String APP_LANGUAGE = "language";
    private SharedPreferences language_pref;

    // функция установки языка перед setContentView()
    void Set(Context context) {

        String languageToLoad = Get(context);

        Configuration config = context.getResources().getConfiguration();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    // функция для записи в pref нового языка
    void Update(String language,Context context) {

        language_pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = language_pref.edit();
        edit.putString(APP_LANGUAGE, language);
        edit.apply();

        Set(context);
    }

    // получение текущего языка
    String Get(Context context){
        language_pref = PreferenceManager.getDefaultSharedPreferences(context);
        String language = language_pref.getString(APP_LANGUAGE, "ru");
        return language;
    }

    // получения текущего языка в полном формате
    String GetFull(Context context){
        String language = Get(context);

        if(language.equals("ru")){
            language = "Русский";
        }else if(language.equals("en")){
            language = "English";
        }
        return language;
    }
}
