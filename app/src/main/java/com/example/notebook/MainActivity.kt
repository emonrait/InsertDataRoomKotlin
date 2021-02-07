package com.example.notebook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notebook.data.GlobalVariable
import com.example.notebook.data.getProgressDrawable
import com.example.notebook.data.loadImage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var updateImageLink = ""
    private lateinit var globalVariable: GlobalVariable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        globalVariable = this.applicationContext as GlobalVariable
        val imagelink =
            "https://1.bp.blogspot.com/-lBVZsV0Q68w/XZ9r_8pasEI/AAAAAAAAe-A/Y12PrSDspn85qT_QlLIIfdOLY9EfmlPUQCLcBGAsYHQ/s1600/DSC_0563.JPG"

        addBtn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        showBtn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        showImage.loadImage(
            imagelink, getProgressDrawable(showImage.context)
        )

        showImage.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(true)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .setAspectRatio(4, 4) // .setRequestedSize(300,800)
                .start(this@MainActivity)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                globalVariable.iamgeLinkroom = resultUri.toString()
                updateImageLink = resultUri.toString()
                showImage.setImageURI(resultUri)
                Toast.makeText(
                    applicationContext,
                    updateImageLink,
                    Toast.LENGTH_LONG
                ).show()
                Log.e("resultCode", updateImageLink.toString())
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

}