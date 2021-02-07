package com.example.notebook

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.notebook.data.User
import com.example.notebook.data.UserViewModel
import com.example.notebook.data.getProgressDrawable
import com.example.notebook.data.loadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.coroutines.launch


class UpdateFragment : Fragment() {

    private lateinit var muserViewModel: UserViewModel
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        muserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.UpdatetextInputEditTextNoteTitle.setText(args.currentUser.noteTitle)
        view.updatetextInputEditTextNoteDescription.setText(args.currentUser.noteDescription)
        view.updateImage.load(args.currentUser.profilePhoto)


        view.btnUpdate.setOnClickListener {
            updateDatatoDatabase()

        }

        view.btnDelete.setOnClickListener {
            deleteUser()
        }


        return view
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext()).setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            muserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Delete Successful ${args.currentUser.id.toString()}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_udateFragment_to_listFragment)

        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setIcon(R.drawable.ic_baseline_delete_24)
        builder.setMessage("Are you Sure you want to Delete ${args.currentUser.id}?")
        builder.setTitle("Delete ${args.currentUser.id.toString()}?")
        builder.create().show()
    }

    private fun updateDatatoDatabase() {
        val id = args.currentUser.id
        val noteTitle = UpdatetextInputEditTextNoteTitle.text.toString()
        val noteDescription = updatetextInputEditTextNoteDescription.text.toString()

        if (inputCheck(noteTitle, noteDescription)) {
            lifecycleScope.launch {
                val updateUser = User(id, noteTitle, noteDescription, getBitmap())
                muserViewModel.updateUser(updateUser)
            }

            Toast.makeText(requireContext(), "Update Successful", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_udateFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Update Unsuccessful! Please input data all field",
                Toast.LENGTH_LONG
            ).show()
        }


    }

    private suspend fun getBitmap(): Bitmap {
        val datapath =
            "https://1.bp.blogspot.com/-lBVZsV0Q68w/XZ9r_8pasEI/AAAAAAAAe-A/Y12PrSDspn85qT_QlLIIfdOLY9EfmlPUQCLcBGAsYHQ/s1600/DSC_0563.JPG"
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(datapath)
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