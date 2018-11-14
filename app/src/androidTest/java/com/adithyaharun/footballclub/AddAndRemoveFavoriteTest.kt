package com.adithyaharun.footballclub

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddAndRemoveFavoriteTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun homeActivityTest() {
        Thread.sleep(5000)

        val _LinearLayout = onView(
                allOf(withId(R.id.match_layout),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("org.jetbrains.anko.recyclerview.v7._RecyclerView")),
                                        3),
                                1),
                        isDisplayed()))
        _LinearLayout.perform(click())

        val actionMenuItemView = onView(
                allOf(withId(R.id.navigation_add_to_favorites), withContentDescription("Add to Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        val appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_favorite_match), withContentDescription("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()))
        bottomNavigationItemView.perform(click())

        val _LinearLayout2 = onView(
                allOf(withId(R.id.match_layout),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("org.jetbrains.anko.recyclerview.v7._RecyclerView")),
                                        0),
                                1),
                        isDisplayed()))
        _LinearLayout2.perform(click())

        val actionMenuItemView2 = onView(
                allOf(withId(R.id.navigation_add_to_favorites), withContentDescription("Add to Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView2.perform(click())

        val appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton2.perform(click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
