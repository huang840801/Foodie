package com.guanhong.foodie.activities;

import static android.support.test.espresso.Espresso.onView;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.guanhong.foodie.R;
import com.guanhong.foodie.profile.ProfileFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FoodieActivityTest {

    private String username_tobe_typed="Ajesh";
    private String correct_password ="password";
    @Rule
    public ActivityTestRule<FoodieActivity> mActivityTestRule = new ActivityTestRule<>(FoodieActivity.class);
    @Test
    public void onCreate() {

//        ProfileFragment profileFragment = new ProfileFragment();
//        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(profileFragment, "").commit();

//        onView(withId(R.id.imageView_custom_tab)).perform(click());
        onView(allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.view_pager)))
                .check(matches(isDisplayed()));
//        onView(withId(R.id.fragment_container)).perform(click());
//        onView(withId(R.id.tab_layout)).perform(click());

//        android.support.test.espresso.ViewInteraction fragmentText = onView(withId(R.id.textView_userEmail));
//
//        fragmentText.check((ViewAssertion) doesNotExist());
//
//        onView(withId(R.id.imageView_user)).perform(click());
//
//        fragmentText.check((ViewAssertion) matches(isDisplayed()));
//        for (int i = 0; i < 20; i++) {
//            onView(withId(R.id.view_pager)).check(matches(isDisplayed()));
//        }
//        onView(withId(R.id.view_pager)).check(matches(withText("")));

    }
}