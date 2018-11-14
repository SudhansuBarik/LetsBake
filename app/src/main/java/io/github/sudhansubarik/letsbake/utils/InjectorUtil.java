package io.github.sudhansubarik.letsbake.utils;

import android.content.Context;

import io.github.sudhansubarik.letsbake.FoodRepository;
import io.github.sudhansubarik.letsbake.helpers.AppPrefHelper;
import io.github.sudhansubarik.letsbake.remote.NetworkDataSource;

public class InjectorUtil {
    public static FoodRepository provideRepository(Context context) {
        // remote
        NetworkDataSource networkDataSource = new NetworkDataSource();
        // pref
        AppPrefHelper preferenceHelper = new AppPrefHelper(context, "movie-pref");

        return FoodRepository.getInstance(networkDataSource, preferenceHelper);
    }
}
