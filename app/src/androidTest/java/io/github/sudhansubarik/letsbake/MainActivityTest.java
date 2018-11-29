package io.github.sudhansubarik.letsbake;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.github.sudhansubarik.letsbake.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickItemOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click item at 1st position
        onView(withId(R.id.menu_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Assert if the text displayed on next page is correct and as expected
        onView(withId(R.id.recipe_ingredients_textView))
                .check(matches(withText("\n• Graham Cracker crumbs (2 CUP)\n• unsalted butter, melted (6 TBLSP)\n• granulated sugar (0.5 CUP)\n• salt (1.5 TSP)\n• vanilla (5 TBLSP)\n• Nutella or other chocolate-hazelnut spread (1 K)\n• Mascapone Cheese(room temperature) (500 G)\n• heavy cream(cold) (1 CUP)\n• cream cheese(softened) (4 OZ)")));
    }

    @Test
    public void clickItemFour() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click item at 4th position
        onView(withId(R.id.menu_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        // Assert if the text displayed on next page is correct and as expected
        onView(withId(R.id.recipe_ingredients_textView))
                .check(matches(withText("\n• Graham Cracker crumbs (2 CUP)\n• unsalted butter, melted (6 TBLSP)\n• granulated sugar (250 G)\n• salt (1 TSP)\n• vanilla,divided (4 TSP)\n• cream cheese, softened (680 G)\n• large whole eggs (3 UNIT)\n• large egg yolks (2 UNIT)\n• heavy cream (250 G)")));
    }
}
