package ru.ucvt.simulatorcasino

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import java.util.Locale

internal class Language {

    private var language_pref: SharedPreferences? = null
    private val APP_LANGUAGE = "language"

    // функция установки языка перед setContentView()
    fun Set(context: Context) {

        val languageToLoad = Get(context)

        val config = context.resources.configuration
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    // функция для записи в pref нового языка
    fun Update(language: String, context: Context) {

        language_pref = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = language_pref!!.edit()
        edit.putString(APP_LANGUAGE, language)
        edit.apply()

        Set(context)
    }

    // получение текущего языка
    fun Get(context: Context): String {
        language_pref = PreferenceManager.getDefaultSharedPreferences(context)
        return language_pref!!.getString(APP_LANGUAGE, "ru")
    }

    // получения текущего языка в полном формате
    fun GetFull(context: Context): String {
        var language = Get(context)

        if (language.equals("ru", ignoreCase = true) || language.equals("русский", ignoreCase = true)) {
            language = "Русский"
        } else {
            language = "English"
        }
        return language
    }
}
