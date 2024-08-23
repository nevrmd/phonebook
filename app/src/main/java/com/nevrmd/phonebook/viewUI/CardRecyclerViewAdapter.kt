package com.nevrmd.phonebook.viewUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nevrmd.phonebook.R
import com.nevrmd.phonebook.databinding.CardItemBinding
import com.nevrmd.phonebook.room.PhoneCard

class CardRecyclerViewAdapter(
    private val phoneCardsList: List<PhoneCard>,
    private val clickListener: (PhoneCard) -> Unit
) : RecyclerView.Adapter<CardViewHolder>() {
    // onCreateViewHolder creates new ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.card_item, parent, false
        )
        return CardViewHolder(binding)
    }

    // Returns phoneCardsList's size
    override fun getItemCount(): Int {
        return phoneCardsList.size
    }

    // Binds ViewHolders
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(phoneCardsList[position], clickListener)
    }
}

// ViewHolder is used to show the RecyclerView elements
class CardViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(phoneCard: PhoneCard, clickListener: (PhoneCard) -> Unit) {
        binding.tvName.text = phoneCard.name
        binding.tvPhone.text = phoneCard.phoneNumber.toString()
        binding.cvPhoneCard.setOnClickListener {
            clickListener(phoneCard)
        }
    }
}