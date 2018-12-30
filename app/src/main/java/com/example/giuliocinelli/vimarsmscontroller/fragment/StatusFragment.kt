package com.example.giuliocinelli.vimarsmscontroller.fragment

import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.giuliocinelli.vimarsmscontroller.R
import com.example.giuliocinelli.vimarsmscontroller.utils.DialogHelper
import com.example.giuliocinelli.vimarsmscontroller.utils.DialogHelper.Companion.showErrorDialog
import com.example.giuliocinelli.vimarsmscontroller.utils.DialogHelper.Companion.showSuccessDialog
import com.example.giuliocinelli.vimarsmscontroller.utils.Prefs
import com.example.giuliocinelli.vimarsmscontroller.utils.SMSReader
import com.example.giuliocinelli.vimarsmscontroller.viewModel.StatusViewModel


class StatusFragment : Fragment() {

    var temperatureStatusEditText: TextView? = null
    var lastCheckEditText: TextView? = null
    var checkButton: Button? = null
    private var prefs: Prefs? = null
    private var handler = Handler()
    private val runnableSyncSMS = Runnable {
        SMSReader.syncSMS(activity!!.applicationContext)
        Log.d("SMS-SYNC", "Lettura messaggio fatta")
        setTextViews()
        startCheckSMS()
    }

    companion object {
        fun newInstance() = StatusFragment()
    }

    private lateinit var viewModel: StatusViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.status_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        setTextViews()
        this.startCheckSMS()
    }

    override fun onPause() {
        super.onPause()
        this.stopCheckSMS()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StatusViewModel::class.java)
        prefs = Prefs(activity!!.applicationContext)
        setButton()
    }

    private fun setTextViews(){
        temperatureStatusEditText = view?.findViewById(R.id.temperature_status)
        lastCheckEditText = view?.findViewById(R.id.last_check)
        temperatureStatusEditText?.text = prefs!!.currentTemperature.toString() + " °C"
        lastCheckEditText?.text = "Dato aggiornato al\n"+prefs!!.lastTimeChecked
    }

    private fun setButton(){
        checkButton = view?.findViewById(R.id.check_button)

        checkButton?.setOnClickListener {
            sendCheckStatusMessage()
        }
    }

    private fun sendCheckStatusMessage(){
        val smsManager = SmsManager.getDefault()

        if (!prefs!!.isAllSettingSaved()){
            showErrorDialog("Non hai impostato il numero di telefono e gli altri campi nelle impostazioni!", activity!!)
            return
        }

        smsManager.sendTextMessage(prefs?.phoneNumber, null, generateMessage(), null, null)
        showSuccessDialog("Messaggio inviato con successo! \n\nAttendi qualche secondo per l'aggiornamento della temperatura.", activity!!)


    }

    private fun generateMessage(): String{
        return "${prefs?.devicePassword}.${prefs?.deviceCode}.STATO"
    }

    private fun startCheckSMS(){
        handler.postDelayed(runnableSyncSMS, 2000)
    }

    private fun stopCheckSMS(){
        handler.removeCallbacks(runnableSyncSMS)
    }

}
