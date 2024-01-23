package com.citibox.courier.sdk.example;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;

import com.citibox.courier.sdk.DeliveryLauncher;
import com.citibox.courier.sdk.domain.DeliveryParams;
import com.citibox.courier.sdk.webview.models.WebAppEnvironment;

import org.jetbrains.annotations.Nullable;

public final class MainActivity extends ComponentActivity {

    private final DeliveryLauncher launcher = new DeliveryLauncher(this, deliveryResult -> {
        Log.d("MainActivity", "Result");
        return null;
    });

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DeliveryParams params = new DeliveryParams(
                "",
                "",
                "",
                false,
                "",
                "",
                WebAppEnvironment.Test
        );
        launcher.launch(params);
    }
}
