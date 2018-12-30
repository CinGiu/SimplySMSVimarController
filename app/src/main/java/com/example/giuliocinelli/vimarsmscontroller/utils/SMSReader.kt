package com.example.giuliocinelli.vimarsmscontroller.utils

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import com.example.giuliocinelli.vimarsmscontroller.viewModel.StatusViewModel
import java.util.*

class SMSReader{

    companion object {

        fun syncSMS(context: Context) {

            val prefs = Prefs(context)
            if (!prefs.isAllSettingSaved()){
                return
            }

            val phoneNumber = prefs.phoneNumber

            val uriSms = Uri.parse("content://sms/inbox")
            val cursor = context.contentResolver.query(uriSms, arrayOf("_id", "address", "date", "body"), null, null, "date DESC")

            cursor!!.moveToFirst()
            var result = ""
            while (cursor.moveToNext()) {
                val address = cursor.getString(1)
                val date: Long = cursor.getLong(2)
                val body = cursor.getString(3)
                if (address == phoneNumber || address == "+39$phoneNumber"){
                    prefs.lastTemperature = parseSettedTemperature(body)
                    prefs.currentTemperature = parseCurrentTemperature(body)
                    prefs.lastTimeChecked = DateUtils.convertLongToTime(date)
                    if (prefs.lastTemperature != 0F && prefs.currentTemperature != 0F){
                        return
                    }
                }
            }
        }

        private fun parseSettedTemperature(msg: String): Float {
            val splittedBody = msg.split("\n")
            if (splittedBody.size <= 3) {
                return 0F
            }
            val lineTemperature= splittedBody[3]
            val splittedSpaces = lineTemperature.split(" ")
            if (splittedSpaces.size == 4){
                val englishTemperature = splittedSpaces[2].replace(',', '.')
                return englishTemperature.toFloat()
            }
            return 0F
        }


        private fun parseCurrentTemperature(msg: String): Float {
            val splittedBody = msg.split("\n")
            if (splittedBody.size <= 3) {
                return 0F
            }
            val lineTemperature= splittedBody[2]
            val splittedSpaces = lineTemperature.split(" ")
            if (splittedSpaces.size == 4){
                val englishTemperature = splittedSpaces[2].replace(',', '.')
                return englishTemperature.toFloat()
            }
            return 0F
        }
    }



}