package com.miniproject.tn.attendancetrackingapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.creativeteam.tn.attendancetrackingapplication.Adapter.StudentAttendanceAdapter
import com.creativeteam.tn.attendancetrackingapplication.Model.StudentAttendance
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_student_attendance_list.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StudentAttendanceListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_attendance_list)
        setTitle("Current Attendance")

        progressBar.visibility = View.VISIBLE

        val date = Calendar.getInstance().time
        val strDateFormat = "MM-dd-yyyy"
        val dateFormat = SimpleDateFormat(strDateFormat)
        val formattedDate = dateFormat.format(date)

        var list = ArrayList<StudentAttendance>()
        var adp = StudentAttendanceAdapter(this, list)
        rv_student_attendance.layoutManager = LinearLayoutManager(this)
        rv_student_attendance.adapter = adp

        val query = ParseQuery<ParseObject>("AttendanceTimes")
        query.whereEqualTo("date", formattedDate)
        query.include("student")
        query.findInBackground{ attendanceList, e ->
            progressBar.visibility = View.GONE
            tvInfo.visibility = View.GONE
            if(e == null){
                if(attendanceList.size == 0){
                    tvInfo.visibility = View.VISIBLE
                }

                for (att in attendanceList){
                    var picture = att.getParseObject("student")!!.getParseFile("picture")
                    var pictureURL= ""
                    if(picture!=null){
                        pictureURL = picture.url
                    }

                    var leavingTime =""
                    if(att.getString("leavingTime")!=null){
                        leavingTime = att.get("leavingTime").toString()
                    }

                    list.add(StudentAttendance(
                            att.objectId,
                            pictureURL,
                            att.getParseObject("student")!!.get("name").toString(),
                            att.getParseObject("student")!!.get("rfid").toString(),
                            att.getParseObject("student")!!.get("ssn").toString(),
                            att.get("entryTime").toString(),
                            leavingTime
                    ))

                }
                adp.notifyDataSetChanged()
            }else{
                Log.d("StudentAttActivity", "Error: "+e.message)
            }
        }

    }
}
