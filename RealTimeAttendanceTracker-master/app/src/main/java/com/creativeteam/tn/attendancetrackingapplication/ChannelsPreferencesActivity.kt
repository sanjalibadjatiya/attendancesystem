package com.miniproject.tn.attendancetrackingapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.parse.ParseInstallation
import com.parse.ParsePush
import kotlinx.android.synthetic.main.activity_channels_preferences.*

class ChannelsPreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels_preferences)
        setTitle("Notification Channels Preferences")

        updateSwitchState()

        swEntryChannel.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                ParsePush.subscribeInBackground("entry")
                Toast.makeText(this, "You are subscribed to Entry Channel!", Toast.LENGTH_LONG).show()
            }else{
                ParsePush.unsubscribeInBackground("entry")
                Toast.makeText(this, "You are unsubscribed from Entry Channel!", Toast.LENGTH_LONG).show()
            }
        }

        swLeavingChannel.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                ParsePush.subscribeInBackground("leaving")
                Toast.makeText(this, "You are subscribed to Leaving Channel!", Toast.LENGTH_LONG).show()
            }else{
                ParsePush.unsubscribeInBackground("leaving")
                Toast.makeText(this, "You are unsubscribed from Leaving Channel!", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun updateSwitchState() {
        val subscribedChannels = ParseInstallation.getCurrentInstallation().getList<String>("channels")

        swEntryChannel.isChecked = subscribedChannels!!.contains("entry")

        swLeavingChannel.isChecked = subscribedChannels!!.contains("leaving")
    }
}
