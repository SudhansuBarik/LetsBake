package io.github.sudhansubarik.letsbake.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import io.github.sudhansubarik.letsbake.App;
import io.github.sudhansubarik.letsbake.room.RecipeResponse;
import io.github.sudhansubarik.letsbake.utils.Constant;
import timber.log.Timber;

public class NetworkDataSource {
    private MutableLiveData<List<RecipeResponse>> mutableRecipeData;

    public NetworkDataSource() {
        this.mutableRecipeData = new MutableLiveData<>();
    }

    public void deserializeToJson(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        mutableRecipeData.setValue(Arrays.asList(gson.fromJson(response, RecipeResponse[].class)));
    }

    private StringRequest getStringRequestForRecipe() {
        return new StringRequest(Request.Method.GET, Constant.RECIPE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        deserializeToJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Timber.e(error);
                    }
                }
        );
    }

    public LiveData<List<RecipeResponse>> getRecipesFromNetwork() {
        MySingleton.getInstance(App.getInstance())
                .addToRequestQueue(getStringRequestForRecipe());
        return mutableRecipeData;
    }
}
