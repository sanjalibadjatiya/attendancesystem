package com.miniproject.tn.attendancetrackingapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Main Menu")

        btnAddNewStudent.setOnClickListener {
            val intent = Intent(this, AddNewStudentActivity :: class.java)
            startActivity(intent)
        }

        btnShowCurrentAttendance.setOnClickListener {
            val intent = Intent(this, StudentAttendanceListActivity :: class.java)
            startActivity(intent)
        }

        btnShowAttendanceHistory.setOnClickListener {
            val intent = Intent(this, StudentAttendanceHistoryActivity :: class.java)
            startActivity(intent)
        }

        btnEditChannels.setOnClickListener {
            val intent = Intent(this, ChannelsPreferencesActivity :: class.java)
            startActivity(intent)
        }

    }
}
