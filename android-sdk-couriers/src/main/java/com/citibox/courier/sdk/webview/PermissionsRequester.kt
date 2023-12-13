package com.citibox.courier.sdk.webview

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsRequester(
    val activity: Activity,
    val permissions: List<String>
) {

    companion object {
        const val PERMISSION_REQUEST_CODE = 0x12354132
    }

    fun askPermissionsRationale(){
        if(!isPermissionGranted()){
            askPermissions()
        }
    }

    private fun askPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            permissions.toTypedArray(),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun isPermissionGranted(): Boolean {
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            )
                return false
        }

        return true
    }


}