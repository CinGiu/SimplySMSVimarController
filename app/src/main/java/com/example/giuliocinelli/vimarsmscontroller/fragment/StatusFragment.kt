package com.example.giuliocinelli.vimarsmscontroller.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.giuliocinelli.vimarsmscontroller.R
import com.example.giuliocinelli.vimarsmscontroller.utils.Prefs
import com.example.giuliocinelli.vimarsmscontroller.viewModel.StatusViewModel


class StatusFragment : Fragment() {

    var temperatureStatusEditText: TextView? = null
    var lastCheckEditText: TextView? = null
    var checkButton: Button? = null
    private var prefs: Prefs? = null

    companion object {
        fun newInstance() = StatusFragment()
    }

    private lateinit var viewModel: StatusViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.status_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StatusViewModel::class.java)
        prefs = Prefs(activity!!.applicationContext)
        setTextViews()
        setButton()

    }

    private fun setTextViews(){
        temperatureStatusEditText = view?.findViewById(R.id.temperature_status)
        lastCheckEditText = view?.findViewById(R.id.last_check)
    }

    private fun setButton(){
        checkButton = view?.findViewById(R.id.check_button)

        checkButton?.setOnClickListener {
            sendCheckStatusMessage()
        }
    }

    fun sendCheckStatusMessage(){
        val smsManager = SmsManager.getDefault()

        if (!prefs!!.isAllSettingSaved()){
            return
        }

        smsManager.sendTextMessage(prefs?.phoneNumber, null, generateMessage(), null, null)
    }

    fun generateMessage(): String{
        return "${prefs?.devicePassword}.${prefs?.deviceCode}.STATO"
    }


}
