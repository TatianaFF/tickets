package com.example.tickets.screens.search_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchDialogViewModel @Inject constructor(

) : ViewModel() {
    private val _hello = MutableStateFlow<String>("Dialog")
    val hello = _hello.asLiveData()
}