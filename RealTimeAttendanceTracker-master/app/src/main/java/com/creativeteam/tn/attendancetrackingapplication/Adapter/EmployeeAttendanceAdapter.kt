package com.miniproject.tn.attendancetrackingapplication.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miniproject.tn.attendancetrackingapplication.Model.StudentAttendance
import com.miniproject.tn.attendancetrackingapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.student_attendance_row.view.*

class StudentAttendanceAdapter(var c: Context, var list: ArrayList<StudentAttendance>) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var my_view= LayoutInflater.from(p0.context).inflate(R.layout.student_attendance_row, p0,false)
        return StudentAttendanceHolder(my_view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

        (p0 as StudentAttendanceHolder).bind(list[p1])

    }

    class StudentAttendanceHolder(view: View) : RecyclerView.ViewHolder(view) {
        var pict_student = view.picture_student
        var student_name = view.tv_student_name
        var student_ssn = view.tv_student_ssn
        var student_entry_time = view.tv_student_entry_time
        var student_leaving_time = view.tv_student_leaving_time

        fun bind(stdAttendance : StudentAttendance){
            student_name.text = "Name: "+stdAttendance.name
            student_ssn.text = "SSN: "+stdAttendance.ssn
            student_entry_time.text = "Entry Time: "+stdAttendance.entryTime
            student_leaving_time.text = "Leaving Time: "+stdAttendance.leavingTime

            if(stdAttendance.pictureURL.length > 0){
                Picasso.get().load(stdAttendance.pictureURL).into(pict_student)
            }else{
                pict_employee.setImageResource(R.drawable.avatar)
            }

        }

    }
}