package io.github.sudhansubarik.letsbake;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.github.sudhansubarik.letsbake.helpers.PrefHelper;
import io.github.sudhansubarik.letsbake.helpers.RepositoryHelper;
import io.github.sudhansubarik.letsbake.remote.NetworkDataSource;
import io.github.sudhansubarik.letsbake.room.RecipeResponse;

public class FoodRepository implements RepositoryHelper {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static FoodRepository sInstance;
    private final NetworkDataSource foodNetworkDataSource;
    private final PrefHelper preferenceHelper;

    private FoodRepository(NetworkDataSource networkSource,
                           PrefHelper preferenceHelper) {
        foodNetworkDataSource = networkSource;
        this.preferenceHelper = preferenceHelper;
    }

    public synchronized static FoodRepository getInstance(
            NetworkDataSource weatherNetworkDataSource,
            PrefHelper preferenceHelper) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new FoodRepository(weatherNetworkDataSource,
                        preferenceHelper);
            }
        }
        return sInstance;
    }

    // get recipe list , check if db-exist or not. if exist fetch data
    @Override
    public LiveData<List<RecipeResponse>> getRecipeList() {
        LiveData<List<RecipeResponse>> responseList;
        responseList = foodNetworkDataSource.getRecipesFromNetwork();
        return responseList;
    }

    // check from preference if db already exist or not
    @Override
    public boolean checkDbExistOrNot() {
        return preferenceHelper.checkDbExistOrNot();
    }

    // set db now exist or deleted
    @Override
    public void setDbExist(boolean dbExist) {
        preferenceHelper.setDbExist(dbExist);
    }

    @Override
    public String getCurrentRecipeIngredient() {
        return preferenceHelper.getCurrentRecipeIngredient();
    }

    @Override
    public void setCurrentRecipeIngredient(String text) {
        preferenceHelper.setCurrentRecipeIngredient(text);
    }
}
