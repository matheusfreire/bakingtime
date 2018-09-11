package com.msf.bakingtime;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.msf.bakingtime.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final int RECIPES_EXPECTED = 4;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testQuantityOnRecycler(){
        try {
            Thread.sleep(TimeUnit.MINUTES.convert(3, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) withId(R.id.recipe_list);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), is(RECIPES_EXPECTED));
    }

}
