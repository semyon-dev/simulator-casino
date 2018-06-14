
package ru.ucvt.simulatorcasino;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import es.dmoral.toasty.Toasty;

public class Settings extends PreferenceActivity {

    Preference btn_new_balance, github_link;
    Balance balance = new Balance();
    Language language = new Language();
    ListPreference set_language, font_size;
    private Font font = new Font();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);

        github_link = findPreference("github_link");
        set_language = (ListPreference) findPreference("language");
        font_size = (ListPreference) findPreference("font_size");
        btn_new_balance = findPreference("btn_new_balance");

        set_language.setValue(language.GetFull(Settings.this));
        font_size.setValue(Float.toString(font.GetFontSize(getApplicationContext())));

        set_language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String languageToLoad = newValue.toString();

                if (languageToLoad.equals("English")){
                    languageToLoad = "en";
                } else if (languageToLoad.equals("Русский")){
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

        font_size.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                Float size = Float.valueOf(newValue.toString());

                font.SetNewFontSize(getBaseContext(), size);

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
