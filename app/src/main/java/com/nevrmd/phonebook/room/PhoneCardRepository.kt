package com.nevrmd.phonebook.room

// Implements logic of where we should fetch the data from
// That's why we don't just use dao for all the things
// It's useful when we have different sources of information
// Right now we have only one (Room)
class PhoneCardRepository(private val dao: PhoneCardDao) {
    val phoneCards = dao.getAllPhoneCardsInDb()

    suspend fun insert(phoneCard: PhoneCard): Long {
        return dao.insertPhoneCard(phoneCard)
    }

    suspend fun delete(phoneCard: PhoneCard) {
        return dao.deletePhoneCard(phoneCard)
    }

    suspend fun update(phoneCard: PhoneCard) {
        return dao.updatePhoneCard(phoneCard)
    }

    suspend fun deleteAll() {
        return dao.deleteAll()
    }
}