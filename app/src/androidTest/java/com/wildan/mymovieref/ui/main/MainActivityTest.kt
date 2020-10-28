package com.wildan.mymovieref.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.wildan.mymovieref.R
import com.wildan.mymovieref.data.repository.FakeRemoteRepository
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private val dummyMovies = FakeRemoteRepository.generatePopularMovies()
    private val dummyTV = FakeRemoteRepository.generatePopularTVSeries()

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loadMovies() {
        onView(withText("MOVIES")).perform(click())
        onView(withId(R.id.listMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.listMovie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size
            )
        )
    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.listMovie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.txtDesc)).check(matches(isDisplayed()))
        Espresso.pressBack()
    }

    @Test
    fun loadTVSeries() {
        onView(withText("TV SERIES")).perform(click())
        onView(withId(R.id.listTV)).check(matches(isDisplayed()))
        onView(withId(R.id.listTV)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTV.size
            )
        )
    }

    @Test
    fun loadDetailTVSeries() {
        onView(withText("TV SERIES")).perform(click())
        onView(withId(R.id.listTV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.txtDesc)).check(matches(isDisplayed()))
        Espresso.pressBack()
    }
}