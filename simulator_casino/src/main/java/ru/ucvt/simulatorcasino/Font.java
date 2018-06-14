package ru.ucvt.simulatorcasino;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;

import java.lang.reflect.Field;

import static android.content.Context.WINDOW_SERVICE;

class Font {

    private static final String APP_FONT_SIZE = "size";
    private SharedPreferences font_pref;


    static void ChangeFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception ex) {
            Crashlytics.log("Can't set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
            Crashlytics.logException(ex);
        }
    }

    void ChangeSize(Configuration configuration, Context context, Resources resources) {
        configuration.fontScale = GetFontSize(context);
        DisplayMetrics metrics = resources.getDisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        context.getResources().updateConfiguration(configuration, metrics);
    }

    float GetFontSize(Context context) {
        font_pref = PreferenceManager.getDefaultSharedPreferences(context);
        float font_size = font_pref.getFloat(APP_FONT_SIZE, 1.0f);
        return font_size;
    }

    void SetNewFontSize(Context context, float size) {
        font_pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = font_pref.edit();
        edit.putFloat(APP_FONT_SIZE, size);
        edit.apply(); // сохраняем изменения
    }
}