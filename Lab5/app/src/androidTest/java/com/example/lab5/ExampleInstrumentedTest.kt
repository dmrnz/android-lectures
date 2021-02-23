package com.example.lab5

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun rotateTest() {
        val stringToBeTyped = "Typed string"

        onView(withId(R.id.act_main__button)).perform(click())
        onView(withId(R.id.act_main__button)).check(matches(withText("Pressed")))
        onView(withId(R.id.act_main__edit_text)).check(matches(withText("")))

        onView(withId(R.id.act_main__edit_text)).perform(typeText(stringToBeTyped))

        scenarioRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        scenarioRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(withId(R.id.act_main__button)).check(matches(withText("Press")))
        onView(withId(R.id.act_main__edit_text)).check(matches(withText(stringToBeTyped)))
    }
}