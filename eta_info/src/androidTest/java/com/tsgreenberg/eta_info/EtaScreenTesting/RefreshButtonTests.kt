package com.tsgreenberg.eta_info.EtaScreenTesting

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.tsgreenberg.eta_info.models.EtaRefreshState
import com.tsgreenberg.eta_info.utils.TestingTags
import com.tsgreenberg.eta_info.ui.screens.RefreshButton
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import java.util.*


class RefreshButtonTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var int = 0

    private val testAction: (Int, Long) -> Unit = { x, y ->
        int++
    }

    private fun setUpRefresh(
        state: EtaRefreshState,
        action: (Int, Long) -> Unit
    ): SemanticsNodeInteraction {
        return composeTestRule.run {
            setContent {
                RefreshButton(1, etaRefreshState = state, action)
            }
            onNodeWithTag(TestingTags.REFRESH_BUTTON, true)
        }
    }

    @Test
    fun testRefreshButtonEnabled() {

        setUpRefresh(EtaRefreshState.Enabled, testAction).performClick()
        assert(int == 1)
    }

    @Test
    fun testRefreshButtonDisabled() {
        var int = 0
        setUpRefresh(EtaRefreshState.Disabled(0), testAction).performClick()
        val isDisabled = int == 0
        assert(isDisabled)
    }

    @Test
    fun testToastEmission() {
        val toastMsg = "Refresh active in 60 seconds"
        setUpRefresh(
            EtaRefreshState.Disabled(Calendar.getInstance().timeInMillis),
            testAction
        ).performClick()
        onView(withText(toastMsg)).inRoot(
            withDecorView(
                not(
                    `is`(composeTestRule.activity.window.decorView)
                )
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

    }


}

