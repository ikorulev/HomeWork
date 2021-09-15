package ru.ikorulev.homework

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ikorulev.homework.presentation.view.MainActivity


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    private var mIdlingResource: IdlingResource? = null

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        activityScenarioRule.scenario.onActivity { activity ->
            mIdlingResource = activity.viewModel.idlingResource
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }

    @Test
    fun onFavoriteClick() {
        //очистить таблицы
        onView(withId(R.id.nav_clear))
            .perform(click())

        //загрузить фильмы
        onView(withId(R.id.nav_download))
            .perform(click())

        //добавить в избранное
        onView(withId(R.id.recyclerFilm)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                clickChildViewWithId(R.id.favourites)
            )
        )
            .perform(click())

        //перейти в избранные
        onView(withId(R.id.nav_favourites))
            .perform(click())

        //проверить фильм что появился на экране
        onView(withId(R.id.favouritesImage))
            .check(matches(isDisplayed()))

    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }

    companion object MyViewAction {
        fun clickChildViewWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController?, view: View) {
                    val v: View = view.findViewById(id)
                    v.performClick()
                }
            }
        }
    }



}