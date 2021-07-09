package com.mutsanna.githubuseruiuxdanapi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.mutsanna.githubuseruiuxdanapi.alarammanager.AlarmReceiver
import java.util.*

class SettingPreferenceManager : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var language: String
    private lateinit var reminder: String
    private lateinit var isSet: SwitchPreference
    private lateinit var languageSetting: Preference
    private val alarmReceiver: AlarmReceiver = AlarmReceiver()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_preference)
        init()
        setSummaries()
    }

    private fun init() {
        reminder = resources.getString(R.string.key_reminder)
        language = resources.getString(R.string.key_language)
        isSet = findPreference<SwitchPreference>(reminder) as SwitchPreference
        languageSetting = findPreference<Preference>(language) as Preference

        languageSetting.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            moveToLanguageSetting()
            true
        }
    }

    private fun moveToLanguageSetting() {
        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isSet.isChecked = sh.getBoolean(reminder, false)

        val languageSum = Locale.getDefault().displayLanguage
        languageSetting.summary = languageSum
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val switchStatus = sharedPreferences.getBoolean(reminder, false)
        if (switchStatus) {
            alarmReceiver.setRepeatAlarm(context)
        } else {
            alarmReceiver.cancelAlarm(context)
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}