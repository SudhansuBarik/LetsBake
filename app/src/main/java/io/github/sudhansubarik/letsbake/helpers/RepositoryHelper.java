package io.github.sudhansubarik.letsbake.helpers;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.github.sudhansubarik.letsbake.room.RecipeResponse;

public interface RepositoryHelper extends PrefHelper {
    LiveData<List<RecipeResponse>> getRecipeList();
}
