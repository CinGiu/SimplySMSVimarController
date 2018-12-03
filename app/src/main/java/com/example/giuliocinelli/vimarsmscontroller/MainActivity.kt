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


class MainActivity : AppCompatActivity() {

    var homeFragment = StatusFragment()
    var setTemperatureFragment  = SetTemperatureFragment()
    var settingsFragment = SettingsFragment()

    var active: Fragment = homeFragment

    private val fm : FragmentManager = supportFragmentManager


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
        supportActionBar?.hide()
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
