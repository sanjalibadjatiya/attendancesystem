package com.miniproject.tn.attendancetrackingapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_add_new_employee.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddNewStudentActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    val GALLERY_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_student)
        setTitle("Add New Student")

        btnOpenCamera.setOnClickListener {
            openCamera()
        }

        btnOpenGallery.setOnClickListener {
            openGallery()
        }

        btnSave.setOnClickListener {
            if(TextUtils.isEmpty(edName.text)){
                edName.error = "This field is required!"
            }else if(TextUtils.isEmpty(edSSN.text)){
                edSSN.error = "This field is required!"
            }else{
                progressBar.visibility = View.VISIBLE
                val query = ParseQuery.getQuery<ParseObject>("Student")
                query.whereEqualTo("ssn", edSSN.text.toString())
                query.countInBackground{count, e ->
                    if(count > 0){
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "The SSN is already exists!", Toast.LENGTH_LONG).show()
                    }else{
                        val data = imageToByteArray(imageView)
                        val file = ParseFile("picture.png", data)
                        file.saveInBackground(SaveCallback { e ->
                            if(e==null){
                                val student = ParseObject("Student")
                                student.put("name", edName.text.toString())
                                student.put("ssn", edSSN.text.toString())
                                student.put("picture", file)
                                student.saveInBackground { err ->
                                    if(err == null){
                                        progressBar.visibility = View.GONE
                                        Toast.makeText(this, "The student is successfully saved!", Toast.LENGTH_LONG).show()
                                    }else{
                                        Log.d("AddNewStudentActivityActivity", "Error : "+err.message)
                                    }
                                }
                            }else{
                                Log.d("AddNewStudentActivityActivity", "Error : "+e.message)
                            }
                        })
                    }
                }
            }
        }

    }

    private fun imageToByteArray(imageView: ImageView): ByteArray {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    private fun openGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        if(photoPickerIntent.resolveActivity(packageManager) != null){
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePictureIntent.resolveActivity(packageManager)!= null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                val imageBitmap = data!!.extras.get("data") as Bitmap
                imageView.setImageBitmap(imageBitmap)
            }else if(requestCode == GALLERY_REQUEST){
                val selectedImage = data!!.getData()
                try{
                    var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    imageView.rotation = -90.0F
                    imageView.setImageBitmap(bitmap)
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }

    }

}
