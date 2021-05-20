package com.miniproject.tn.attendancetrackingapplication

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.creativeteam.tn.attendancetrackingapplication.Adapter.EmployeeAttendanceAdapter
import com.creativeteam.tn.attendancetrackingapplication.Model.EmployeeAttendance
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_employee_attendance_history.*
import java.text.SimpleDateFormat
import java.util.*

class StudentAttendanceHistoryActivity : AppCompatActivity() {

    var cal =Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_attendance_history)
        setTitle("Attendance History")

        showDatePicker()
        btn_select_date.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM-dd-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val selectedDate = sdf.format(cal.getTime())
            tv_attendance_history_selected_date.text = "Selected date: "+selectedDate

            retrieveAttendanceHistory(selectedDate)
        }

        DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun retrieveAttendanceHistory(selectedDate: String) {
        var list = ArrayList<StudentAttendance>()
        var adp = StudentAttendanceAdapter(this, list)
        rv_student_attendance_history.layoutManager = LinearLayoutManager(this)
        rv_student_attendance_history.adapter = adp

        val query = ParseQuery<ParseObject>("AttendanceTimes")
        query.whereEqualTo("date", selectedDate)
        query.include("employee")
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
