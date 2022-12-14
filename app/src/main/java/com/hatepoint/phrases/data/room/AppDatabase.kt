package com.hatepoint.phrases.data.room

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hatepoint.phrases.App
import com.hatepoint.phrases.data.room.entity.Phrase

@Database(entities = [Phrase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun phraseDao(): PhraseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private var LOCK = Any()
        fun getInstance(context: Context) : AppDatabase {
            synchronized(LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                        AppDatabase::class.java,
                        "phrases.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }

    }
}