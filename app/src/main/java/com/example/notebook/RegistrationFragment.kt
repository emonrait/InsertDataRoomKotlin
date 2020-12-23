package com.example.notebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notebook.data.User
import com.example.notebook.data.UserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*

class RegistrationFragment : Fragment() {
    private lateinit var muserViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        muserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.Register.setOnClickListener {
            insertDatatoDatabase()

        }
        return view
    }

    private fun insertDatatoDatabase() {
        val noteTitle = textInputEditTextNoteTitle.text.toString()
        val noteDescription = textInputEditTextNoteDescription.text.toString()

        if (inputCheck(noteTitle, noteDescription)) {
            val user = User(0, noteTitle, noteDescription)
            muserViewModel.addUser(user)
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

    private fun inputCheck(
        noteTitle: String,
        noteDescription: String
    ): Boolean {
        return !(noteTitle.isEmpty() && noteDescription.isEmpty())

    }
}