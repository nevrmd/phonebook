package com.nevrmd.phonebook.myViewModel

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
        if (isUpdateOrDelete) {
            // Update
            phoneCardToUpdateOrDelete.name = inputName.value!!
            phoneCardToUpdateOrDelete.phoneNumber = inputPhoneNumber.value?.toInt()!!
            update(phoneCardToUpdateOrDelete)
        } else {
            // Save
            // The vals are non-nullable since we don't need it in our database
            val name = inputName.value!!
            val phoneNumber = inputPhoneNumber.value!!.toInt()
            insert(PhoneCard(0, name, phoneNumber))
            inputName.value = null
            inputPhoneNumber.value = null
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(phoneCardToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun insert(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.insert(phoneCard)
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    private fun update(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.update(phoneCard)
        // Resetting the buttons and fields
        inputName.value = null
        inputPhoneNumber.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun delete(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.delete(phoneCard)
        // Resetting the buttons and fields
        inputName.value = null
        inputPhoneNumber.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateAndDelete(phoneCard: PhoneCard) {
        inputName.value = phoneCard.name
        inputPhoneNumber.value = phoneCard.phoneNumber.toString()
        isUpdateOrDelete = true
        phoneCardToUpdateOrDelete = phoneCard
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    // Nothing is going to be here since we use Observable interface
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}