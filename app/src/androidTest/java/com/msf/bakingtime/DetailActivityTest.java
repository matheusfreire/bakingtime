package com.msf.bakingtime;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.msf.bakingtime.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    private static final int POSITION_TO_CLICK = 1;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testOnClick(){
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_TO_CLICK, click()));
        try {
            Thread.sleep(TimeUnit.MINUTES.convert(3, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ingredient_label)).check(matches(withText(R.string.ingredients)));
    }
}
