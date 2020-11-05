@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.wildan.mymovieref.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun String.formatToMMMddyyyy(defaultPattern: String): String {
    val formatter = SimpleDateFormat(defaultPattern, Locale.getDefault())
    val sdf = SimpleDateFormat("MMM, dd yyyy", Locale.getDefault())
    return sdf.format(formatter.parse(this))
}

fun String.errorLog(message: String) {
    Log.e(this, message)
}

fun String.debugLog(message: String) {
    Log.d(this, message)
}

fun String.infoLog(message: String) {
    Log.i(this, message)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showRetrySnackbar(message: String, onClick: () -> Unit) {
    val snack =
        Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    snack.setAction("RETRY", View.OnClickListener {
        // executed when retry is clicked
        onClick()
    })
    snack.show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}





