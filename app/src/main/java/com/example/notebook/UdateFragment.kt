package com.example.notebook

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notebook.data.User
import com.example.notebook.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_udate.*
import kotlinx.android.synthetic.main.fragment_udate.view.*


class UdateFragment : Fragment() {

    private lateinit var muserViewModel: UserViewModel
    private val args by navArgs<UdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_udate, container, false)

        muserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.UpdatetextInputEditTextNoteTitle.setText(args.currentUser.noteTitle)
        view.updatetextInputEditTextNoteDescription.setText(args.currentUser.noteDescription)

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
            val updateUser = User(id, noteTitle, noteDescription)
            muserViewModel.updateUser(updateUser)
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

    private fun inputCheck(
        noteTitle: String,
        noteDescription: String
    ): Boolean {
        return !(noteTitle.isEmpty() && noteDescription.isEmpty())

    }

}