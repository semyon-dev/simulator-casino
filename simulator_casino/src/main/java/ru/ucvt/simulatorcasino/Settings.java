
package ru.ucvt.simulatorcasino;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import es.dmoral.toasty.Toasty;

public class Settings extends PreferenceActivity {

    private Preference btn_new_balance, github_link;
    private Balance balance = new Balance();
    private Language language = new Language();
    private ListPreference set_language, text_size;
    private Font font = new Font();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);

        github_link = findPreference("github_link");
        set_language = (ListPreference) findPreference("language");
        text_size = (ListPreference) findPreference("text_size");
        btn_new_balance = findPreference("btn_new_balance");

        set_language.setValue(language.GetFull(Settings.this));

        float size = font.GetTextSize(getApplicationContext());
        byte index;

        if (size == 0.75f) {
            index = 0;
        } else if (size == 1.0f) {
            index = 1;
        } else if (size == 1.25f) {
            index = 2;
        } else {
            index = 3;
        }

        text_size.setValueIndex(index);

        set_language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                String languageToLoad = newValue.toString();

                if (languageToLoad.equals("English")) {
                    languageToLoad = "en";
                } else if (languageToLoad.equals("Русский")) {
                    languageToLoad = "ru";
                }

                language.Update(languageToLoad, getApplicationContext());

                Intent intent = new Intent(Settings.this, MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                System.exit(0);

                return true;
            }
        });

        text_size.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                String size = newValue.toString();
                float size_f = 1.0f;

                switch (size) {
                    case "Small":
                    case "Маленький":
                        size_f = 0.75f;
                        break;
                    case "Medium":
                    case "Средний":
                        size_f = 1.0f;
                        break;
                    case "Large":
                    case "Большой":
                        size_f = 1.25f;
                        break;
                    case "Very large":
                    case "Огромный":
                        size_f = 1.5f;
                        break;
                }

                font.SetNewFontSize(getBaseContext(), size_f);

                Intent intent = new Intent(Settings.this, MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                System.exit(0);

                return true;
            }
        });

        btn_new_balance.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (balance.Get(Settings.this) == 0) {
                    balance.Update(100, Settings.this);
                    Toasty.success(Settings.this, getString(R.string.new_money)).show();
                } else {
                    Toasty.error(Settings.this, getString(R.string.balance_more_than_zero)).show();
                }
                return true;
            }
        });

        github_link.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SemyonNovikov/SimulatorCasino"));
                startActivity(browserIntent);
                return true;
            }
        });
    }
}
