package com.citibox.courier.sdk.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.citibox.courier.sdk.TestTagDelivery
import com.citibox.courier.sdk.TestTagRetrieval

fun ComposeHolder.testRobot(func: TestRobot.() -> Unit): TestRobot {
    return TestRobot(this.composeTestRule).apply(func)
}

class TestRobot(
    private val testRule: ComposeContentTestRule
) {

    fun clickDelivery() {
        testRule
            .onNodeWithTag(TestTagDelivery)
            .performClick()
    }

    fun clickRetrieval() {
        testRule
            .onNodeWithTag(TestTagRetrieval)
            .performClick()
    }

}