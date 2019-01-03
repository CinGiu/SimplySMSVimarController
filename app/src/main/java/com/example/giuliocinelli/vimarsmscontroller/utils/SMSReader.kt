package com.example.giuliocinelli.vimarsmscontroller.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat

class SMSReader{

    companion object {
        lateinit var prefs : Prefs

        fun syncSMS(context: Context) {
            if(ContextCompat.checkSelfPermission(context, "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
                val uriSms = Uri.parse("content://sms/inbox")
                val cursor = context.contentResolver.query(uriSms, arrayOf("_id", "address", "date", "body"), null, null, "date DESC")
                        ?: return

                if (cursor.count == 0) {
                    return
                }

                cursor.moveToFirst()
                do {
                    val address = cursor.getString(1)
                    val date: Long = cursor.getLong(2)
                    val body = cursor.getString(3)
                    manageSMS(context, address, body, date)
                    if (prefs.lastTemperature != 0F && prefs.currentTemperature != 0F){
                        return
                    }
                } while (cursor.moveToNext())
            }

        }

        fun manageSMS(context: Context, address: String, body: String, date: Long){

            prefs = Prefs(context)

            if (!prefs.isAllSettingSaved()){
                return
            }

            val phoneNumber = prefs.phoneNumber
            if (address == phoneNumber || address == "+39$phoneNumber"){
                prefs.lastTemperature = parseSettedTemperature(body)
                prefs.currentTemperature = parseCurrentTemperature(body)
                prefs.lastTimeChecked = DateUtils.convertLongToTime(date)

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