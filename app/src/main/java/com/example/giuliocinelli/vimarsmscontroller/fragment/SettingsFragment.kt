package com.example.giuliocinelli.vimarsmscontroller.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.giuliocinelli.vimarsmscontroller.R
import com.example.giuliocinelli.vimarsmscontroller.utils.Prefs
import com.example.giuliocinelli.vimarsmscontroller.viewModel.SettingsViewModel


class SettingsFragment : Fragment() {

    var phoneEditText : EditText? = null
    var codeEditText: EditText? = null
    var passwordEditText: EditText? = null
    var saveButton: Button? = null
    private var prefs: Prefs? = null

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        prefs = Prefs(activity!!.applicationContext)
        return inflater.inflate(R.layout.settings_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        setEditTexts()
        setSaveButton()

    }

    fun checkAllFields() : Boolean {
        if (phoneEditText?.text.toString() == ""){
            return false
        }

        if (codeEditText?.text.toString() == ""){
            return false
        }

        if (passwordEditText?.text.toString() == ""){
            return false
        }

        return true
    }

    private fun setEditTexts() {
        phoneEditText = view?.findViewById(R.id.phone_number)
        phoneEditText?.setText(prefs?.phoneNumber)

        codeEditText = view?.findViewById(R.id.device_code)
        codeEditText?.setText(prefs?.deviceCode)

        passwordEditText = view?.findViewById(R.id.password)
        passwordEditText?.setText(prefs?.devicePassword)
    }

    private fun setSaveButton(){
        saveButton = view?.findViewById(R.id.save_button)

        saveButton?.setOnClickListener {
            if (checkAllFields()){
                prefs?.phoneNumber = passwordEditText?.text.toString()
                prefs?.deviceCode = codeEditText?.text.toString()
                prefs?.devicePassword = passwordEditText?.text.toString()

            }else{
                showErrorDialog("Devi compilare tutti i campi")
            }
        }
    }

    private fun showErrorDialog(error: String){
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Attenzione!")
        builder.setMessage(error)

        builder.setPositiveButton(android.R.string.yes) { _, _ ->

        }

        builder.show()
    }

}
