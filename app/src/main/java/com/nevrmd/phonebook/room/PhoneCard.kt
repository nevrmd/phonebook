package com.nevrmd.phonebook.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhoneCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val phoneNumber: Int
)
