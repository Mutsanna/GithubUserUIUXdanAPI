package com.mutsanna.githubuseruiuxdanapi

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class SettingPreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_preference)

        supportFragmentManager.beginTransaction()
            .replace(R.id.id_setting, SettingPreferenceManager())
            .commit()

        supportActionBar?.title = "Setting"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}