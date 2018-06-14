package ru.ucvt.simulatorcasino;

import android.content.Context;
import android.graphics.Typeface;

import com.crashlytics.android.Crashlytics;

import java.lang.reflect.Field;

public class TypeFaceUtil {

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
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
}
