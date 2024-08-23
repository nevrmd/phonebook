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

    // These contain liveData of two editTexts and state of the buttons
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputPhoneNumber = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    // Setting default state of the buttons
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear"
    }
    // Saves or updates info in the database based on the buttons state
    // Delete is when cardView is not pressed
    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            // Update
            phoneCardToUpdateOrDelete.name = inputName.value.toString()
            phoneCardToUpdateOrDelete.phoneNumber = inputPhoneNumber.value?.toLong()
            update(phoneCardToUpdateOrDelete)
        } else {
            // Save
            // The vals are non-nullable since we don't need it in the database
            val name = inputName.value.toString()
            val phoneNumber = inputPhoneNumber.value?.toLong()
            insert(PhoneCard(0, name, phoneNumber))
            inputName.value = null
            inputPhoneNumber.value = null
        }
    }

    // Clears all or deletes specific info in the database based on the buttons state
    // Delete is when cardView is not pressed
    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(phoneCardToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    // Insert
    private fun insert(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.insert(phoneCard)
    }

    // Clear all
    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    // Update
    private fun update(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.update(phoneCard)
        // Resetting the buttons and fields when the card update is made
        inputName.value = null
        inputPhoneNumber.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear"
    }

    // Delete
    private fun delete(phoneCard: PhoneCard) = viewModelScope.launch {
        repository.delete(phoneCard)
        // Resetting the buttons and fields when the card gets deleted
        inputName.value = null
        inputPhoneNumber.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear"
    }

    // Initiating update or delete by filling out the editTexts and changing the buttons state
    // Used in listItemClicked in MainActivity
    fun initUpdateAndDelete(phoneCard: PhoneCard) {
        inputName.value = phoneCard.name
        inputPhoneNumber.value = phoneCard.phoneNumber.toString()
        isUpdateOrDelete = true
        phoneCardToUpdateOrDelete = phoneCard
        saveOrUpdateButtonText.value = "Upd"
        clearAllOrDeleteButtonText.value = "Del"
    }

    // Nothing is going to be here since we use Observable interface
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}