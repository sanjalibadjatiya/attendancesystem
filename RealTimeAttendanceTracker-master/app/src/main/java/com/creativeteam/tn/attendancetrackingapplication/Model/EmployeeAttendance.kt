package com.miniproject.tn.attendancetrackingapplication.Model

data class StudentAttendance (var objectId: String,
                               var pictureURL: String,
                               var name: String,
                               var rfid: String,
                               var ssn: String,
                               var entryTime: String,
                               var leavingTime: String) {
}