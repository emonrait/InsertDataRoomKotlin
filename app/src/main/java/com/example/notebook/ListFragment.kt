package com.example.notebook

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebook.data.ListAdapter
import com.example.notebook.data.User
import com.example.notebook.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //  Recylerview
        val adapter = ListAdapter()
        val recylerview = view.recylerview
        recylerview.adapter = adapter
        recylerview.layoutManager = LinearLayoutManager(requireContext())

        // User View Model
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuAddicon) {
            findNavController().navigate(R.id.action_listFragment_to_registrationFragment)

            Toast.makeText(requireContext(), "Test", Toast.LENGTH_SHORT).show()

        } else if (item.itemId == R.id.menudeleteicon) {
            deleteAllUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext()).setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteAllUser()
            Toast.makeText(
                requireContext(),
                "Everything Delete Successful",
                Toast.LENGTH_SHORT
            ).show()

        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setIcon(R.drawable.ic_baseline_delete_24)
        builder.setMessage("Are you Sure you want to Delete Everything?")
        builder.setTitle("Delete Everything?")
        builder.create().show()
    }
}
