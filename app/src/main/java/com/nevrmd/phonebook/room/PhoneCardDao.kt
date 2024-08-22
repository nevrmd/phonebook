package com.nevrmd.phonebook.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PhoneCardDao {
    @Insert
    suspend fun insertPhoneCard(phoneCard: PhoneCard): Long

    @Update
    suspend fun updatePhoneCard(phoneCard: PhoneCard)

    @Delete
    suspend fun deletePhoneCard(phoneCard: PhoneCard)

    @Query("DELETE FROM PhoneCard")
    suspend fun deleteAll()

    @Query("SELECT * FROM PhoneCard")
    fun getAllPhoneCardsInDb(): LiveData<List<PhoneCard>>
}