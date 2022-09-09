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


//fun SemanticsNodeInteraction.assertTextColor(
//    color: Color
//): SemanticsNodeInteraction = assert(isOfColor(color))
//
//private fun isOfColor(color: Color): SemanticsMatcher = SemanticsMatcher(
//    "${SemanticsProperties.Text.name} is of color '$color'"
//) {
//    val textLayoutResults = mutableListOf<TextLayoutResult>()
//    it.config.getOrNull(SemanticsActions.GetTextLayoutResult)
//        ?.action
//        ?.invoke(textLayoutResults)
//    return@SemanticsMatcher if (textLayoutResults.isEmpty()) {
//        false
//    } else {
//        textLayoutResults.first().layoutInput.style.color == color
//    }
//}



class RefreshButtonTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
//
//
//    @get:Rule
//    val composeTestRule = createComposeRule()


    private fun setUpRefresh(state: EtaRefreshState, action: () -> Unit): SemanticsNodeInteraction {
        return composeTestRule.run {
            setContent {
                RefreshButton(1, etaRefreshState = state) {
                    action()
                }
            }
            onNodeWithTag(TestingTags.REFRESH_BUTTON, true)
        }
    }

    @Test
    fun testRefreshButtonEnabled() {
        var int = 0
        setUpRefresh(EtaRefreshState.Enabled) {
            int++
        }.apply {
            performClick()
        }
        assert(int == 1)
    }

    @Test
    fun testRefreshButtonDisabled() {
        var int = 0
        setUpRefresh(EtaRefreshState.Disabled(0)) {
            int++
        }.apply {
            performClick()
        }
        val isDisabled = int == 0
        assert(isDisabled)
    }

    @Test
    fun testToastEmission() {
        val toastMsg = "Refresh active in 60 seconds"
        setUpRefresh(EtaRefreshState.Disabled(Calendar.getInstance().timeInMillis)) {
        }.apply {
            performClick()
        }
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

