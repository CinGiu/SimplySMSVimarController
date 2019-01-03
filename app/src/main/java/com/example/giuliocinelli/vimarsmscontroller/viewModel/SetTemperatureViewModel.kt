package com.example.giuliocinelli.vimarsmscontroller.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.giuliocinelli.vimarsmscontroller.utils.DialogHelper
import com.example.giuliocinelli.vimarsmscontroller.utils.Prefs

enum class SendResult{
    NoError, NoPhoneNumber, NoCode, NoPassword, NoPermissions
}

class SetTemperatureViewModel : ViewModel() {

    private lateinit var prefs: Prefs
    var numericTemperature : Int = 0
    private val _temperature = MutableLiveData<String>()
    val temperature: LiveData<String>
        get() = _temperature


    fun handlePreferences(context: Context){
        prefs = Prefs(context)
        _temperature.value = prefs.lastTemperature.toString() + " °C"
        numericTemperature = prefs.lastTemperature.toInt()
    }

    fun setTemperature(i: Int){
        prefs.lastTemperature = i.toFloat()
        numericTemperature = i
        _temperature.value = i.toString() + " °C"
    }

    fun sendTemperature(context: Context): SendResult {
        Log.println(Log.INFO,"TEMPERATURE TO SET", "temperature: $numericTemperature" )

        val message = generateMessage()

        if (message == "" ){
            return SendResult.NoPassword
        }

        val phoneNumber = getPhoneNumber()

        if (phoneNumber == "" ){
            return SendResult.NoPhoneNumber
        }

        if(ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED) {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            return SendResult.NoError
        }else{
            return SendResult.NoPermissions

        }

    }

    private fun generateMessage(): String{
        return prefs.devicePassword+"."+prefs.deviceCode+".MAN."+numericTemperature
    }

    private fun getPhoneNumber(): String{
        return prefs.phoneNumber
    }
}
