package com.gramasanjeevini.utils

import android.content.Context
import android.widget.Toast

object NotificationHelper {
    fun showLocalAlert(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
