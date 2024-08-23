package com.nevrmd.phonebook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevrmd.phonebook.databinding.ActivityMainBinding
import com.nevrmd.phonebook.room.PhoneCard
import com.nevrmd.phonebook.room.PhoneCardDb
import com.nevrmd.phonebook.room.PhoneCardRepository
import com.nevrmd.phonebook.myViewModel.PhoneCardViewModel
import com.nevrmd.phonebook.myViewModel.PhoneCardViewModelFactory
import com.nevrmd.phonebook.viewUI.CardRecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var phoneCardViewModel: PhoneCardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Room
        val dao = PhoneCardDb.getInstance(application).phoneCardDao
        val repository = PhoneCardRepository(dao)
        val factory = PhoneCardViewModelFactory(repository)

        phoneCardViewModel = ViewModelProvider(this, factory)[PhoneCardViewModel::class.java]
        binding.phoneCardViewModel = phoneCardViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.rvPhoneCards.layoutManager = LinearLayoutManager(this)
        DisplayPhoneCardsList()
    }

    private fun DisplayPhoneCardsList() {
        phoneCardViewModel.phoneCards.observe(this, Observer {
            binding.rvPhoneCards.adapter = CardRecyclerViewAdapter(
                it
            ) { selectedItem: PhoneCard -> listItemClicked(selectedItem) }
        })
    }

    private fun listItemClicked(selectedItem: PhoneCard) {
        Toast.makeText(
            this, "Selected name is ${selectedItem.name}", Toast.LENGTH_SHORT
        ).show()
        phoneCardViewModel.initUpdateAndDelete(selectedItem)
    }
}