package com.project.petwalk


import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.project.petwalk.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getImage.setOnClickListener {

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Fetching image....")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val imageName = binding.etimageId.text.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName.jpg")

            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                if(progressDialog.isShowing)
                    progressDialog.dismiss()
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imageview.setImageBitmap(bitmap)

            }.addOnFailureListener {
                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                Toast.makeText(this,"Failed to retrieve the image", Toast.LENGTH_SHORT).show()

            }
        }


    }
}