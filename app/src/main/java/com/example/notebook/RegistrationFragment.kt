package com.example.notebook

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.notebook.data.*
import com.google.android.material.snackbar.Snackbar
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {
    private lateinit var muserViewModel: UserViewModel

    private lateinit var globalVariable: GlobalVariable
    private lateinit var iamgeview: ImageView
    var updateImageLink = ""
    val imagelink =
        "https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        muserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        iamgeview = view.findViewById(R.id.addImage)
        globalVariable = activity?.applicationContext as GlobalVariable
        //Toast.makeText(, "", Toast.LENGTH_SHORT).show()

        view.Register.setOnClickListener {
            insertDatatoDatabase()

        }
        iamgeview.loadImage(
            globalVariable.iamgeLinkroom, getProgressDrawable(iamgeview.context)
        )

        iamgeview.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(true)
                .setAspectRatio(4, 4) // .setRequestedSize(300,800)
                .start(requireActivity())
        }

        return view
    }

    private fun insertDatatoDatabase() {
        val noteTitle = textInputEditTextNoteTitle.text.toString()
        val noteDescription = textInputEditTextNoteDescription.text.toString()

        if (inputCheck(noteTitle, noteDescription)) {
            lifecycleScope.launch {
                val user = User(0, noteTitle, noteDescription, getBitmap())
                muserViewModel.addUser(user)
            }

            textInputEditTextNoteTitle.setText("")
            textInputEditTextNoteDescription.setText("")
            Snackbar.make(scrollView, "Registration Successful", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registrationFragment_to_listFragment)
        } else {
            Snackbar.make(
                scrollView,
                "Registration Unsuccessful! Please input data all field",
                Snackbar.LENGTH_LONG
            ).show()
        }


    }

    private suspend fun getBitmap(): Bitmap {
        val datapath =
            "https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4"
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(globalVariable.iamgeLinkroom)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun inputCheck(
        noteTitle: String,
        noteDescription: String
    ): Boolean {
        return !(noteTitle.isEmpty() && noteDescription.isEmpty())

    }

}