package com.example.notebook.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "User_table", indices = arrayOf(
        Index(
            value = ["noteTitle", "noteDescription"],
            unique = true
        )
    )
)
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteDescription: String

) : Parcelable