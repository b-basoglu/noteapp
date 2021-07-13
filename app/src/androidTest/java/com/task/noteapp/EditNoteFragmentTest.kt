package com.task.noteapp


import android.accessibilityservice.AccessibilityService
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import org.junit.rules.RuleChain

@HiltAndroidTest
class EditNoteFragmentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)
    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)
    @Before
    fun jumpToPlantDetailFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.container).navigate(R.id.edit_note_fragment)
            }
        }
    }
    @Test
    fun testShareTextIntent() {
        Intents.init()
        Espresso.onView(withId(R.id.bt_save)).perform(ViewActions.click())
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)

    }
}
