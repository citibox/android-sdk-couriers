package com.citibox.courier.sdk.example.retrieval

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.citibox.courier.sdk.RetrievalLauncher
import com.citibox.courier.sdk.domain.RetrievalResult
import com.citibox.courier.sdk.example.retrieval.compose.RetrievalScreen
import com.citibox.courier.sdk.example.ui.theme.AndroidsdkcouriersTheme

class RetrievalActivity : ComponentActivity() {

    private val viewModel: RetrievalViewModel by viewModels()

    private val citiboxRetrievalLauncher = RetrievalLauncher(this, ::retrievalResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidsdkcouriersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RetrievalScreen(
                        viewModel = viewModel,
                        onLaunch = citiboxRetrievalLauncher::launch,
                        onBack = ::finish,
                    )
                }
            }
        }
    }

    private fun retrievalResult(result: RetrievalResult) {
        viewModel.retrievalResult(result = result)
    }
}
