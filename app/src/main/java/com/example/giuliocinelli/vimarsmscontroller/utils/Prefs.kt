package com.example.giuliocinelli.vimarsmscontroller.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    private val _PREFS_FILENAME = "it.giuliocinelli.vimarsmscontroller"
    private val _PHONE_NUMBER = "phone_number"
    private val _DEVICE_CODE = "device_code"
    private val _PASSWORD = "password"

    private val _TEMPERATURE = "temperature"

    private val prefs: SharedPreferences = context.getSharedPreferences(_PREFS_FILENAME, 0);

    var phoneNumber: String
        get() = prefs.getString(_PHONE_NUMBER, "")
        set(value) = prefs.edit().putString(_PHONE_NUMBER, value).apply()

    var deviceCode: String
        get() = prefs.getString(_DEVICE_CODE, "")
        set(value) = prefs.edit().putString(_DEVICE_CODE, value).apply()

    var devicePassword: String
        get() = prefs.getString(_PASSWORD, "")
        set(value) = prefs.edit().putString(_PASSWORD, value).apply()

    var lastTemperature: Int
        get() = prefs.getInt(_TEMPERATURE, 0)
        set(value) = prefs.edit().putInt(_TEMPERATURE, value).apply()

    fun isAllSettingSaved() : Boolean{
        if (phoneNumber == ""){
            return false
        }
        if (deviceCode == ""){
            return false
        }
        if (devicePassword == ""){
            return false
        }
        return true
    }
}