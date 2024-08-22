package com.nevrmd.phonebook.viewModel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevrmd.phonebook.room.PhoneCard
import com.nevrmd.phonebook.room.PhoneCardRepository
import kotlinx.coroutines.launch

// ViewModel holds and processes data
// ViewModel() tells kotlin that it's a viewModel
class PhoneCardViewModel(private val repository: PhoneCardRepository) : ViewModel(), Observable {
    val phoneCards = repository.phoneCards
    private var isUpdateOrDelete = false
    private lateinit var phoneCardToUpdateOrDelete: PhoneCard

    // These contain liveData of two editTexts
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputPhoneNumber = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        // The vals are non-nullable since we don't need it in our database
        val name = inputName.value!!
        val phoneNumber = inputPhoneNumber.value!!
        insert(PhoneCard(0, name, phoneNumber.toInt()))
        inputName.value = null
        inputPhoneNumber.value = null
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    private fun insert(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.insert(phoneCard)
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    private fun update(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.update(phoneCard)
    }

    fun delete(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.delete(phoneCard)
    }

    // Nothing is going to be here since we use Observable interface
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}