package com.hatepoint.phrases

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.hatepoint.phrases.data.PhrasesRepository
import com.hatepoint.phrases.ui.MainViewModel

class MainViewHolderFactory(val application: Application, val repository: PhrasesRepository) : ViewModelProvider.Factory {


    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application, repository) as T
    }
}