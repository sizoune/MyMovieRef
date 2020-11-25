package com.wildan.mymovieref.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.wildan.mymovieref.R
import com.wildan.mymovieref.data.repository.FakeRemoteRepository
import com.wildan.mymovieref.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {
    private val dummyMovies = FakeRemoteRepository.getDummyPopularMovie()
    private val dummyTV = FakeRemoteRepository.getDummyPopularSeries()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withText("MOVIES")).perform(click())
        onView(withId(R.id.listMovie)).check(matches(isDisplayed()))
        dummyMovies?.let {
            onView(withId(R.id.listMovie)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    dummyMovies.size
                )
            )
        }
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
        dummyTV?.let {
            onView(withId(R.id.listTV)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    dummyTV.size
                )
            )
        }
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

    @Test
    fun loadFavoritesMovies(){
        onView(withText("MOVIES")).perform(click())
        onView(withId(R.id.listMovie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.btnFavorite)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.myFav)).perform(click())
        onView(withId(R.id.listMovie)).check(matches(isDisplayed()))
        Espresso.pressBack()
    }

    @Test
    fun loadDetailFavMovie(){
        onView(withId(R.id.myFav)).perform(click())
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
    fun loadFavoritesTV(){
        onView(withText("TV SERIES")).perform(click())
        onView(withId(R.id.listTV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.btnFavorite)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.myFav)).perform(click())
        onView(withText("TV SERIES")).perform(click())
        onView(withId(R.id.listTV)).check(matches(isDisplayed()))
        Espresso.pressBack()
    }

    @Test
    fun loadFavoritesTVDetail(){
        onView(withId(R.id.myFav)).perform(click())
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