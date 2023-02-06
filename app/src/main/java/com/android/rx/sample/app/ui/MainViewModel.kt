package com.android.rx.sample.app.ui

import androidx.lifecycle.ViewModel
import com.android.rx.sample.app.data.MainRepository
import com.android.rx.sample.app.utils.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val counter = mainRepository.counter
}