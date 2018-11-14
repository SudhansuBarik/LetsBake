package io.github.sudhansubarik.letsbake.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.github.sudhansubarik.letsbake.FoodRepository;
import io.github.sudhansubarik.letsbake.room.RecipeResponse;
import io.github.sudhansubarik.letsbake.utils.InjectorUtil;

public class MainActivityViewModel extends AndroidViewModel {

    private LiveData<List<RecipeResponse>> recipeLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        FoodRepository repository = InjectorUtil.provideRepository(application.getApplicationContext());
        recipeLiveData = repository.getRecipeList();
    }
    LiveData<List<RecipeResponse>> getRecipeLiveData() {
        return recipeLiveData;
    }
}
