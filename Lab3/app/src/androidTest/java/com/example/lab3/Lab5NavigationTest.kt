package com.example.lab3

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lab3.task5.ActivityMain
import org.junit.Assert
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class Lab5NavigationTest {

    private fun testShownMainActivity() {
        onView(withId(R.id.task5_activity_main__layout)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun testNotShownMainActivity() {
        onView(withId(R.id.task5_activity_main__layout)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun testShownFragment1() {
        testShownMainActivity()
        onView(withId(R.id.task5_fragment1__layout)).check(matches(isDisplayed()))
        onView(withId(R.id.task5_fragment2__layout)).check(doesNotExist())
        onView(withId(R.id.task5_fragment3__layout)).check(doesNotExist())
        onView(withId(R.id.activity_about__layout)).check(doesNotExist())
    }

    private fun testShownFragment2() {
        testShownMainActivity()
        onView(withId(R.id.task5_fragment1__layout)).check(doesNotExist())
        onView(withId(R.id.task5_fragment2__layout)).check(matches(isDisplayed()))
        onView(withId(R.id.task5_fragment3__layout)).check(doesNotExist())
        onView(withId(R.id.activity_about__layout)).check(doesNotExist())
    }

    private fun testShownFragment3() {
        testShownMainActivity()
        onView(withId(R.id.task5_fragment1__layout)).check(doesNotExist())
        onView(withId(R.id.task5_fragment2__layout)).check(doesNotExist())
        onView(withId(R.id.task5_fragment3__layout)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_about__layout)).check(doesNotExist())
    }

    private fun testShownActivityAbout() {
        testNotShownMainActivity()
        onView(withId(R.id.task5_fragment1__layout)).check(doesNotExist())
        onView(withId(R.id.task5_fragment2__layout)).check(doesNotExist())
        onView(withId(R.id.task5_fragment3__layout)).check(doesNotExist())
        onView(withId(R.id.activity_about__layout)).check(matches(isDisplayed()))
    }

    private fun testFirstToAbout() {
        testShownFragment1()
        onView(withId(R.id.navigation_about)).perform(click())
        testShownActivityAbout()
        pressBack()
        testShownFragment1()
    }

    private fun testSecondToAbout() {
        testShownFragment2()
        onView(withId(R.id.navigation_about)).perform(click())
        testShownActivityAbout()
        pressBack()
        testShownFragment2()
    }

    private fun testThirdToAbout() {
        testShownFragment3()
        onView(withId(R.id.navigation_about)).perform(click())
        testShownActivityAbout()
        pressBack()
        testShownFragment3()
    }

    @get:Rule
    val scenarioRule = ActivityScenarioRule(ActivityMain::class.java)

    @Test
    fun testTransition123() {
        testShownFragment1()
        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        testShownFragment2()
        onView(withId(R.id.task5_fragment2__to_first_button)).perform(click())
        testShownFragment1()
        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        testShownFragment2()
        onView(withId(R.id.task5_fragment2__to_third_button)).perform(click())
        testShownFragment3()
        onView(withId(R.id.task5_fragment3__to_second_button)).perform(click())
        testShownFragment2()
        onView(withId(R.id.task5_fragment2__to_third_button)).perform(click())
        testShownFragment3()
        onView(withId(R.id.task5_fragment3__to_first_button)).perform(click())
        testShownFragment1()
    }

    @Test
    fun testTransitionToAbout() {
        testFirstToAbout()
        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        testSecondToAbout()
        onView(withId(R.id.task5_fragment2__to_third_button)).perform(click())
        testThirdToAbout()
    }

    @Test
    fun testBackStack() {
        testShownFragment1()
        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        pressBack()
        testShownFragment1()

        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        onView(withId(R.id.task5_fragment2__to_third_button)).perform(click())
        pressBack()
        testShownFragment2()

        onView(withId(R.id.task5_fragment2__to_third_button)).perform(click())
        pressBack()
        pressBack()
        testShownFragment1()

        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        onView(withId(R.id.task5_fragment2__to_third_button)).perform(click())
        onView(withId(R.id.navigation_about)).perform(click())
        pressBack()
        testShownFragment3()
        pressBack()
        testShownFragment2()
        pressBack()
        testShownFragment1()
    }

    @Test
    fun testBackStackThrows() {
        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        onView(withId(R.id.task5_fragment2__to_third_button)).perform(click())
        onView(withId(R.id.navigation_about)).perform(click())
        pressBack()
        pressBack()
        pressBack()
        Assert.assertThrows(NoActivityResumedException::class.java, Espresso::pressBack)
    }

    @Test
    fun testStateAfterRotation() {
        testShownFragment1()
        onView(withId(R.id.task5_fragment1__to_second_button)).perform(click())
        testShownFragment2()

        scenarioRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        scenarioRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        testShownFragment2()
    }
}
