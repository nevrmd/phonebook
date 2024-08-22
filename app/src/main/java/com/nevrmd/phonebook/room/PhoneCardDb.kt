package com.nevrmd.phonebook.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([PhoneCard::class], version = 1)
abstract class PhoneCardDb : RoomDatabase() {
    abstract val phoneCardDao: PhoneCardDao

    companion object {
        @Volatile
        private var INSTANCE: PhoneCardDb? = null
        fun getInstance(context: Context): PhoneCardDb {
            synchronized(this) {
                var instance = INSTANCE
                // Checking if there any database object created before
                // If not - create one
                if (instance == null) {
                    // Creating the database object
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhoneCardDb::class.java,
                        "PhoneCard_db"
                    ).build()
                }
                return instance
            }
        }
    }
}