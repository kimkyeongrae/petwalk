package com.project.petwalk

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.project.petwalk.databinding.ActivityStorageBinding
import java.text.SimpleDateFormat
import java.util.*

class StorageActivity :AppCompatActivity() {
    lateinit var binding : ActivityStorageBinding
    lateinit var ImageUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectImageBtn.setOnClickListener {

            selectImage()

        }
        binding.uploadImageBtn.setOnClickListener {

            uploadImage()
        }
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == androidx.appcompat.app.AppCompatActivity.RESULT_OK){


            ImageUri = data?.data!!
            binding.firebaseImage.setImageURI(ImageUri)
        }
    }
    private fun uploadImage() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File....")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("Images/$fileName")

        storageReference.putFile(ImageUri).
        addOnSuccessListener {

            binding.firebaseImage.setImageURI(null)
            Toast.makeText(this@StorageActivity,"Successfuly uploaded", Toast.LENGTH_SHORT).show()
            if(progressDialog.isShowing) progressDialog.dismiss()
        }.addOnCanceledListener {

            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@StorageActivity,"failed", Toast.LENGTH_SHORT).show()
        }



    }

}