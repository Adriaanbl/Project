package es.abd.project.Fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import es.abd.project.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}