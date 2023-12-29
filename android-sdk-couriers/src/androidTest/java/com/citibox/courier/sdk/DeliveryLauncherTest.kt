package com.citibox.courier.sdk

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import com.citibox.courier.sdk.robots.ComposeHolder
import com.citibox.courier.sdk.robots.testRobot
import com.citibox.courier.sdk.webview.WebViewActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeliveryLauncherTest : ComposeHolder {

    @get:Rule
    override val composeTestRule = createAndroidComposeRule<TestingActivity>()

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun release() {
        Intents.release()
    }

    @Test
    fun shouldOpenWebView_whenDeeplinkFails() {
        givenDeeplinkFail()

        testRobot {
            clickDelivery()
        }

        isDeeplinkLaunched()
        isWebViewLaunched()
    }

    @Test
    fun shouldLaunchDeeplink_whenClicked() {
        givenDeeplinkResultOk()

        testRobot {
            clickDelivery()
        }

        isDeeplinkLaunched()
    }

    private fun isWebViewLaunched() {
        Intents.intended(IntentMatchers.hasComponent(WebViewActivity::class.java.canonicalName))
    }

    private fun isDeeplinkLaunched() {
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
    }

    private fun givenDeeplinkResultOk() {
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_VIEW)).respondWithFunction {
            val result =
                if (it.data?.scheme == "app" && it.data?.authority == "couriers.citibox.com") {
                    Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
                } else {
                    throw Exception()
                }

            return@respondWithFunction result
        }
    }

    private fun givenDeeplinkFail() {
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_VIEW)).respondWithFunction {
            val result =
                if (it.data?.scheme == "app" && it.data?.authority == "couriers.citibox.com") {
                    throw Exception()
                } else {
                    Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
                }

            return@respondWithFunction result
        }
    }
}
