package com.example.manu.firstapp

import android.os.Bundle
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat


/**
 * Created by manu on 11/09/17.
 */

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState : Bundle?, rootKey :String?) {

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences)

        val preference = findPreference(SettingsActivity.KEY_PREF_PSEUDO)
        if (preference != null) {
            val sharedPreferences = preferenceScreen.sharedPreferences
            val value = sharedPreferences.getString(preference.key, "")
            preference.setSummary(value)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    override fun onSharedPreferenceChanged(sharedPreferences :SharedPreferences , key : String) {

        if (key.equals(SettingsActivity.KEY_PREF_PSEUDO)) {
            val preference = findPreference(key)
            if (preference != null) {
                val value = sharedPreferences.getString(preference.key, "")
                preference.setSummary(value)
            }
            //val editor = prefs!!.edit()
            // Set summary to be the user-description for the selected value
            //editor.putString(KEY_PREF_PSEUDO, sharedPreferences.getString(key, ""))
        }

    }


}


class SettingsActivity : AppCompatActivity() {

    companion object {
        val KEY_PREF_PSEUDO :String = "babyo_pseudo"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Display the fragment as the main content.
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, SettingsFragment())
                .commit()
        // my_child_toolbar is defined in the layout file
        //val myChildToolbar = findViewById<View>(R.id.bab_toolb) as Toolbar
        //setSupportActionBar(myChildToolbar)

        // Get a support ActionBar corresponding to this toolbar
        val ab = supportActionBar

        // Enable the Up button
        ab!!.setDisplayHomeAsUpEnabled(true)


    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {

        /*
         * Normally, calling setDisplayHomeAsUpEnabled(true)
         * (we do so in onCreate here) as well as
         * declaring the parent activity in the
         * AndroidManifest is all that is required to get the
         * up button working properly. However, in this case,
         * we want to navigate to the previous
         * screen the user came from when the up
         * button was clicked, rather than a single
         * designated Activity in the Manifest.
         *
         * We use the up button's ID (android.R.id.home)
         * to listen for when the up button is
         * clicked and then call onBackPressed
         * to navigate to the previous Activity when this happens.
         */

        val id = item.getItemId()

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this)
        }

        return super.onOptionsItemSelected(item)
    }*/

}