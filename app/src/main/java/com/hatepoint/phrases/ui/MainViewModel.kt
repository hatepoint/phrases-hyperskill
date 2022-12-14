package com.hatepoint.phrases.ui

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hatepoint.phrases.Notification
import com.hatepoint.phrases.R
import com.hatepoint.phrases.data.PhrasesRepository
import com.hatepoint.phrases.data.room.entity.Phrase
import java.util.*

class MainViewModel(val application: Application, val repository: PhrasesRepository) : ViewModel() {

    private lateinit var alarmManager: AlarmManager
    var phrasesList: MutableLiveData<List<Phrase>> = MutableLiveData()
    private var notificationManager = application.getSystemService<NotificationManager>()

    init {
        phrasesList.postValue(repository.getAll())
    }

    fun insert(phrase: Phrase) {
        repository.insert(phrase)
        phrasesList.postValue(repository.getAll())
    }

    fun delete(phrase: Phrase) {
        repository.delete(phrase)
        phrasesList.postValue(repository.getAll())
    }

    //this exists solely for stage 2 or whatever it's gonna be
    @RequiresApi(Build.VERSION_CODES.O)
    private fun postNotification(text: String) {
        val builder = android.app.Notification.Builder(application, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Phrases")
            .setContentText(text)
            .setStyle(android.app.Notification.BigTextStyle())
            .setAutoCancel(true)

        notificationManager?.notify(1, builder.build())
    }

    fun scheduleNotification(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 0)
        val intent = Intent(application, Notification::class.java)
        val title = "Your phrase of the day"
        val message = phrasesList.value?.random()?.phrase
        intent.putExtra("titleExtra", title)
        intent.putExtra("messageExtra", message)

        val pendingIntent = PendingIntent.getBroadcast(application.applicationContext, NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager = application.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

}