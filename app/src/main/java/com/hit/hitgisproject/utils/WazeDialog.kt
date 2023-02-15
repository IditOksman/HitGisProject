package com.hit.hitgisproject.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class WazeDialog(context: Context) : AlertDialog.Builder(context) {

    lateinit var onResponse: (r : ResponseType) -> Unit

    enum class ResponseType {
        YES, NO, FAVOURITE
    }

    fun show(title: String, message: String, listener: (r : ResponseType) -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        onResponse = listener

        // performing positive action
        builder.setPositiveButton("Yes") { _, _ ->
            onResponse(ResponseType.YES)
        }

        // performing negative action
        builder.setNegativeButton("No") { _, _ ->
            onResponse(ResponseType.NO)
        }

        builder.setNeutralButton("Set Favourite") { _, _ ->
            onResponse(ResponseType.FAVOURITE)
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}