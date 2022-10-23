package com.hatepoint.phrases

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.hatepoint.phrases.data.room.AppDatabase
import com.hatepoint.phrases.ui.CHANNEL_ID

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Phrases"
            val descriptionText = "Phrases to boost your mood"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = android.app.NotificationChannel(CHANNEL_ID, name, importance).apply { description = descriptionText }

            notificationManager.createNotificationChannel(channel)
        }
    }
}