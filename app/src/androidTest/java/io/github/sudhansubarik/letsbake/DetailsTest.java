package io.github.sudhansubarik.letsbake;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.sudhansubarik.letsbake.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void detailsTest() {
        ViewInteraction recyclerView = onView(allOf(withId(R.id.menu_list),
                childAtPosition(withClassName(is("android.widget.FrameLayout")), 0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recipe_steps_recyclerView),
                        childAtPosition(withClassName(is("android.widget.LinearLayout")), 1)));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recipe_steps_recyclerView),
                        childAtPosition(withClassName(is("android.widget.LinearLayout")), 1)));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4952);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.step_list_linearLayout),
                        childAtPosition(allOf(withId(R.id.recipe_steps_recyclerView),
                                childAtPosition(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class), 1)),
                                0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction relativeLayout2 = onView(
                allOf(withId(R.id.step_list_linearLayout),
                        childAtPosition(allOf(withId(R.id.recipe_steps_recyclerView),
                                childAtPosition(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class), 1)),
                                0),
                        isDisplayed()));
        relativeLayout2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.recipe_ingredients_textView), withText("• Graham Cracker crumbs (2 CUP)\n• unsalted butter, melted (6 TBLSP)\n• granulated sugar (0.5 CUP)\n• salt (1.5 TSP)\n• vanilla (5 TBLSP)\n• Nutella or other chocolate-hazelnut spread (1 K)\n• Mascapone Cheese(room temperature) (500 G)\n• heavy cream(cold) (1 CUP)\n• cream cheese(softened) (4 OZ)"),
                        childAtPosition(
                                childAtPosition(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class), 0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("• Graham Cracker crumbs (2 CUP) • unsalted butter, melted (6 TBLSP) • granulated sugar (0.5 CUP) • salt (1.5 TSP) • vanilla (5 TBLSP) • Nutella or other chocolate-hazelnut spread (1 K) • Mascapone Cheese(room temperature) (500 G) • heavy cream(cold) (1 CUP) • cream cheese(softened) (4 OZ)")));
    }
}