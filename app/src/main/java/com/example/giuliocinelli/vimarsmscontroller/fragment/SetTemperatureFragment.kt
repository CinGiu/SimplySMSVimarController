package com.example.giuliocinelli.vimarsmscontroller.fragment


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.example.giuliocinelli.vimarsmscontroller.R
import com.example.giuliocinelli.vimarsmscontroller.utils.DialogHelper
import com.example.giuliocinelli.vimarsmscontroller.utils.DialogHelper.Companion.showErrorDialog
import com.example.giuliocinelli.vimarsmscontroller.utils.DialogHelper.Companion.showSuccessDialog
import com.example.giuliocinelli.vimarsmscontroller.viewModel.SendResult
import com.example.giuliocinelli.vimarsmscontroller.viewModel.SetTemperatureViewModel


class SetTemperatureFragment : Fragment() {

    var temperatureValueTextView : TextView? = null
    var seekBar: SeekBar? = null
    var sendTemperatureButton: Button? = null

    companion object {
        fun newInstance() = SetTemperatureFragment()
    }

    private lateinit var viewModel: SetTemperatureViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.set_temperature_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SetTemperatureViewModel::class.java)
        viewModel.handlePreferences(activity!!.applicationContext)

        viewModel.temperature.observe(this, Observer<String> {
            temperatureValueTextView?.text = it
        })

        setSeekBar()
        setSendButton()
    }

    private fun setSendButton() {
        sendTemperatureButton = view?.findViewById(R.id.send_temperature_button)
        sendTemperatureButton?.setOnClickListener {
            when (viewModel.sendTemperature()){
                SendResult.NoPhoneNumber -> showErrorDialog("Non hai inserito il numero di telefono nelle impostazioni!", activity!!)
                SendResult.NoCode -> showErrorDialog("Non hai inserito il CODICE DISPOSITIVO nelle impostazioni!", activity!!)
                SendResult.NoPassword -> showErrorDialog("Non hai inserito la password nelle impostazioni!", activity!!)
                SendResult.NoError -> showSuccessDialog("Messaggio inviato correttamente", activity!!)
            }
        }
    }

    private fun setSeekBar() {
        temperatureValueTextView = view?.findViewById(R.id.temperatureValueTextView)
        seekBar = view?.findViewById(R.id.temperature_seek_bar)

        // Set a SeekBar change listener
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                viewModel.setTemperature(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something

            }
        })
        seekBar?.progress = viewModel.numericTemperature
    }
}
