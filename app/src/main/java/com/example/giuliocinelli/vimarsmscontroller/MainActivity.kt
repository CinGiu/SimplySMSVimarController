package com.example.giuliocinelli.vimarsmscontroller

import android.Manifest
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.giuliocinelli.vimarsmscontroller.fragment.SetTemperatureFragment
import com.example.giuliocinelli.vimarsmscontroller.fragment.SettingsFragment
import com.example.giuliocinelli.vimarsmscontroller.fragment.StatusFragment
import android.provider.Telephony.Sms
import android.content.ContentResolver
import android.net.Uri


class MainActivity : AppCompatActivity() {

    private var homeFragment = StatusFragment()
    private var setTemperatureFragment  = SetTemperatureFragment()
    private var settingsFragment = SettingsFragment()

    private val fm : FragmentManager = supportFragmentManager


    private val _MY_PERMISSIONS_REQUEST_SEND_SMS = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                this.showHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_set_temperature -> {
                this.showSetTemperature()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                this.showSettings()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if(savedInstanceState == null){
            this.showHome()
        }
        askSMSPermission()
    }

    private fun showHome(){
        fm.beginTransaction().replace(R.id.container, homeFragment, "1").commit()
    }

    private fun showSettings(){
        fm.beginTransaction().replace(R.id.container, settingsFragment, "3").commit()
    }

    private fun showSetTemperature(){
        fm.beginTransaction().replace(R.id.container, setTemperatureFragment, "2").commit()
    }

    private fun askSMSPermission() {

        if (
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS),
                        _MY_PERMISSIONS_REQUEST_SEND_SMS)
            }
        }else{
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            _MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(applicationContext,
                            "Devi garantire i permessi per inviare SMS da questa applicazione!", Toast.LENGTH_LONG).show()
                    return
                }
            }
        }

    }
}
