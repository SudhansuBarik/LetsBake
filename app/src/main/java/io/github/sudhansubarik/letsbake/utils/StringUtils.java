package io.github.sudhansubarik.letsbake.utils;

import android.content.Context;

import java.util.Locale;

import io.github.sudhansubarik.letsbake.R;

public class StringUtils {

    public static String formatIngredient(Context context, String name, float quantity, String measure) {

        String line = context.getResources().getString(R.string.recipe_details_ingredient_line);

        String quantityStr = String.format(Locale.UK, "%s", quantity);
        if (quantity == (long) quantity) {
            quantityStr = String.format(Locale.UK, "%d", (long) quantity);
        }
        return String.format(Locale.UK, line, name, quantityStr, measure);
    }
}
