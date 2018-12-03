package com.example.giuliocinelli.vimarsmscontroller.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import com.example.giuliocinelli.vimarsmscontroller.utils.Prefs

enum class SendResult{
    NoError, NoPhoneNumber, NoCode, NoPassword
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
        numericTemperature = prefs.lastTemperature
    }

    fun setTemperature(i: Int){
        prefs.lastTemperature = i
        numericTemperature = i
        _temperature.value = i.toString() + " °C"
    }

    fun sendTemperature(): SendResult {
        Log.println(Log.INFO,"TEMPERATURE TO SET", "temperature: $numericTemperature" )

        val message = generateMessage()

        if (message == "" ){
            return SendResult.NoPassword
        }

        val phoneNumber = getPhoneNumber()

        if (phoneNumber == "" ){
            return SendResult.NoPhoneNumber
        }

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        return SendResult.NoError
    }

    private fun generateMessage(): String{
        return prefs.devicePassword+""+prefs.deviceCode+".MAN."+numericTemperature
    }

    private fun getPhoneNumber(): String{
        return prefs.phoneNumber
    }
}
