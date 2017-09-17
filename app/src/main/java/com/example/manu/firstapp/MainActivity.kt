package com.example.manu.firstapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater




class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE"
    private val ACTION_FOR_PLAYERS = "PLAYERS_ACTION"


    /*private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.options -> {
                // User chose the "Settings" item, show the app settings UI...
                val intentSetting = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intentSetting)
                return true
            }
            R.id.remove_play -> {
                val byRestC = BabyoRestClientUsage(this@MainActivity, ACTION_FOR_PLAYERS)
                byRestC.removePlay()
                return true
            }
            else ->
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
        }
    }

    public override fun onResume() {
        super.onResume()
        this.registerReceiver(receiver, IntentFilter(ACTION_FOR_INTENT_CALLBACK))
        this.registerReceiver(receiver, IntentFilter(ACTION_FOR_PLAYERS))
    }

    public override fun onPause() {
        super.onPause()
        this.unregisterReceiver(receiver)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val myToolbar = findViewById<View>(R.id.bab_toolb) as Toolbar
        //setSupportActionBar(myToolbar)

        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            val notifChannel = NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_LOW)
            notifChannel.enableLights(true)
            notifChannel.enableVibration(true)
            notificationManager!!.createNotificationChannel(notifChannel)
        }


        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (intent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras!!.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]

        FirebaseMessaging.getInstance().subscribeToTopic("baby")

        val YoButt = findViewById<View>(R.id.YoButton) as Button
        YoButt.setOnClickListener {

            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val pseudo = prefs.getString(SettingsActivity.KEY_PREF_PSEUDO, "")
            if (pseudo.equals("monpseudo")) {
                Toast.makeText(this@MainActivity, getString(R.string.error_pseudo), Toast.LENGTH_SHORT).show()
            }
            else{
                val byRestC = BabyoRestClientUsage(this@MainActivity, ACTION_FOR_PLAYERS)
                byRestC.sendNotif(pseudo)

                val msg = getString(R.string.msg_yobutton)
                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            }
        }

        val byRestC = BabyoRestClientUsage(this@MainActivity, ACTION_FOR_INTENT_CALLBACK)

        byRestC.getPlayersName()

        }
        //val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        // val pseudo = prefs.getString(SettingsActivity.KEY_PREF_PSEUDO, "")
        //if (pseudo.isNullOrBlank() ) {

        //}
        //val pseudob = prefs.getString(SettingsActivity.KEY_PREF_PSEUDO, "")
        //Log.d(TAG, "Le pseudo est $pseudob")


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // clear the progress indicator
            //if (progress != null) {
            //    progress.dismiss()
            //}
            when (intent.action) {
                ACTION_FOR_INTENT_CALLBACK -> {
                    message.text = intent.getStringExtra(BabyoRestClientUsage.HTTP_RESPONSE)
                }
                ACTION_FOR_PLAYERS ->{
                    val byRestC = BabyoRestClientUsage(this@MainActivity, ACTION_FOR_INTENT_CALLBACK)
                    byRestC.getPlayersName()
                }
            }
            //
            // my old json code was here. this is where you will parse it.
            //
        }
    }


}