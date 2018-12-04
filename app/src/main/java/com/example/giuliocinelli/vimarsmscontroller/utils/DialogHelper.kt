package com.example.giuliocinelli.vimarsmscontroller.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DialogHelper {
    companion object {
        fun showErrorDialog(error: String, context: Context){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Attenzione!")
            builder.setMessage(error)

            builder.setPositiveButton(android.R.string.yes) { _, _ ->

            }

            builder.show()
        }

        fun showSuccessDialog(error: String, context: Context){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Complimenti!")
            builder.setMessage(error)

            builder.setPositiveButton(android.R.string.yes) { _, _ ->

            }

            builder.show()
        }

        fun showInfoDialog(error: String, context: Context){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("")
            builder.setMessage(error)

            builder.setPositiveButton(android.R.string.yes) { _, _ ->

            }

            builder.show()
        }
    }

}