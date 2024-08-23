package com.nevrmd.phonebook.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhoneCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var phoneNumber: Long?
)
