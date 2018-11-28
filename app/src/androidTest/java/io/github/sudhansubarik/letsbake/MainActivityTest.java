package io.github.sudhansubarik.letsbake;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.github.sudhansubarik.letsbake.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
    }
}
