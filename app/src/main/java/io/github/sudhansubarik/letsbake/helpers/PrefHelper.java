package io.github.sudhansubarik.letsbake.helpers;

public interface PrefHelper {
    boolean checkDbExistOrNot();

    void setDbExist(boolean dbExist);
    String getCurrentRecipeIngredient();
    void setCurrentRecipeIngredient(String text);
}
