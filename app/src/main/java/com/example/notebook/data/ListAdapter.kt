package com.example.notebook.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.notebook.ListFragmentDirections
import com.example.notebook.R
import kotlinx.android.synthetic.main.list_item.view.*


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var userlist = emptyList<User>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userlist[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.noteTitle_txt.text = currentItem.noteTitle.toString()
        holder.itemView.noteDescription_txt.text = currentItem.noteDescription.toString()
        holder.itemView.imageView.load(currentItem.profilePhoto)

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    fun setData(user: List<User>) {
        this.userlist = user
        notifyDataSetChanged()

    }
}