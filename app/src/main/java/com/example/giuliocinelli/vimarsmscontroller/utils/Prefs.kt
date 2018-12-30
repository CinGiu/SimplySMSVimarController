package com.example.giuliocinelli.vimarsmscontroller.utils

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Prefs (context: Context) {
    private val _PREFS_FILENAME = "it.giuliocinelli.vimarsmscontroller"
    private val _PHONE_NUMBER = "phone_number"
    private val _DEVICE_CODE = "device_code"
    private val _PASSWORD = "password"
    private val _CURRENT_TEMPERATURE = "current_temperature"
    private val _LAST_UPDATE = "last_update"
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

    var lastTemperature: Float
        get() = prefs.getFloat(_TEMPERATURE, 0F)
        set(value) = prefs.edit().putFloat(_TEMPERATURE, value).apply()

    var currentTemperature: Float
        get() = prefs.getFloat(_CURRENT_TEMPERATURE, 0F)
        set(value) = prefs.edit().putFloat(_CURRENT_TEMPERATURE, value).apply()

    var lastTimeChecked: String
        get() = prefs.getString(_LAST_UPDATE, "0")
        set(value) = prefs.edit().putString(_LAST_UPDATE, value).apply()

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