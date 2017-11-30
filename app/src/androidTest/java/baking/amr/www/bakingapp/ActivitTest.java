package baking.amr.www.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by amr on 11/30/17.
 */

@RunWith(AndroidJUnit4.class)

public class ActivitTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule=
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        mIdlingResource=mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);

    }



    @Test
    public void checkText_MainActivity() {
        onView(withId(R.id.recipeRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
    }
    @Test
    public void checkPlayerViewIsVisible_RecipeDetailActivity() {
        onView(ViewMatchers.withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.stepsRecylerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.stepDetailLayout)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource(){
        if (mIdlingResource!=null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }



}
