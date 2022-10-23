package com.hatepoint.phrases.ui

import android.app.NotificationManager
import android.app.TimePickerDialog.OnTimeSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatepoint.phrases.*
import com.hatepoint.phrases.data.PhrasesAdapter
import com.hatepoint.phrases.data.PhrasesRepository
import com.hatepoint.phrases.databinding.ActivityMainBinding
import com.hatepoint.phrases.data.room.entity.Phrase

const val CHANNEL_ID = "com.hatepoint.phrases"
const val NOTIFICATION_ID = 1

class MainActivity : AppCompatActivity(), OnTimeSetListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var repository: PhrasesRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = PhrasesRepository(application)
        viewModel = ViewModelProvider(this, MainViewHolderFactory(application, repository)).get(MainViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //TODO: Check if alarm is already up and set the textView accordingly
        viewModel.phrasesList.observe(this) { phrases ->
            binding.recyclerView.adapter = PhrasesAdapter(phrases, viewModel)
        }

        binding.reminderTextView.setOnClickListener {
            val timePicker = TimePickerDialog()
            timePicker.show(supportFragmentManager, "timePicker")
        }

        binding.floatingActionButton.setOnClickListener {
            AddPhraseDialog().show(supportFragmentManager, "addPhraseDialog")
            supportFragmentManager.setFragmentResultListener(
                AddPhraseDialog.REQUEST_KEY, this
            ) { _, result ->
                val phrase = result.getString("phrase")
                if (phrase != null) {
                    viewModel.insert(Phrase(0, phrase))
                }
            }
        }
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        viewModel.scheduleNotification(hour, minute)
        binding.reminderTextView.text = "Reminder set for $hour:$minute"
    }
}