package com.android.logtools

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startFloatWindow = start_float_window as Button
        startFloatWindow.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(applicationContext)) {
                    //启动Activity让用户授权
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    intent.data = Uri.parse("package:" + packageName)
                    startActivityForResult(intent, 100)
                } else {
                    val intent = Intent(this, FloatWindowService::class.java)
                    startService(intent)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    val intent = Intent(this, FloatWindowService::class.java)
                    startService(intent)
                } else {
                    Toast.makeText(this, "ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
