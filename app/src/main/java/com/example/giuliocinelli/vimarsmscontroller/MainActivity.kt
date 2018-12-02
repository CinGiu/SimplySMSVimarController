package com.example.giuliocinelli.vimarsmscontroller

import android.Manifest
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.giuliocinelli.vimarsmscontroller.fragment.SetTemperatureFragment
import com.example.giuliocinelli.vimarsmscontroller.fragment.SettingsFragment
import com.example.giuliocinelli.vimarsmscontroller.fragment.StatusFragment


class MainActivity : AppCompatActivity() {

    var homeFragment = StatusFragment()
    var setTemperatureFragment  = SetTemperatureFragment()
    var settingsFragment = SettingsFragment()

    var active: android.support.v4.app.Fragment = homeFragment

    private val fm : android.support.v4.app.FragmentManager = supportFragmentManager


    private val _MY_PERMISSIONS_REQUEST_SEND_SMS = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fm.beginTransaction().hide(active).show(homeFragment).commit()
                active = homeFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_set_temperature -> {
                fm.beginTransaction().hide(active).show(setTemperatureFragment).commit()
                active = setTemperatureFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                fm.beginTransaction().hide(active).show(settingsFragment).commit()
                active = settingsFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fm.beginTransaction().add(R.id.container, settingsFragment, "3").hide(settingsFragment).commit()
        fm.beginTransaction().add(R.id.container, setTemperatureFragment, "2").hide(setTemperatureFragment).commit()
        fm.beginTransaction().add(R.id.container, homeFragment, "1").commit()

        askSMSPermission()
    }


    private fun askSMSPermission() {

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.SEND_SMS),
                        _MY_PERMISSIONS_REQUEST_SEND_SMS)
            }
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
