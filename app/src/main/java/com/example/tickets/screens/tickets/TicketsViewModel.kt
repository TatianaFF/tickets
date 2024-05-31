package com.example.tickets.screens.tickets

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.domain.GetOffersUseCase
import com.example.models.Offer
import com.example.tickets.adapter.DelegateAdapterItem
import com.example.tickets.model.OfferItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase
) : ViewModel() {
    private val _offers = MutableLiveData<List<DelegateAdapterItem>>()
    val offers: LiveData<List<DelegateAdapterItem>>
        get() = _offers

    init {
        fetchOffers()
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            getOffersUseCase.execute()
                .onStart { Log.e("start", "") }
                .onEach { offersServer ->
                    _offers.postValue(offersServer.map { offerServer ->
                        OfferItem.offerToOfferItem(offerServer)
                    })
                }
                .catch { Log.e("catch", it.toString()) }
                .launchIn(viewModelScope)
        }
    }
}