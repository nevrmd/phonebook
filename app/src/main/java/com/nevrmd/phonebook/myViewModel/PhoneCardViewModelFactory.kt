package com.nevrmd.phonebook.myViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.nevrmd.phonebook.room.PhoneCardRepository
import java.lang.IllegalArgumentException

class PhoneCardViewModelFactory(private val repository: PhoneCardRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if(modelClass.isAssignableFrom(PhoneCardViewModel::class.java)) {
            return PhoneCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}