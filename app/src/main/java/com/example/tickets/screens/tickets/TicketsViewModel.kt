package com.example.tickets.screens.tickets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetOffersUseCase
import com.example.tickets.adapter.DelegateAdapterItem
import com.example.tickets.model.OfferItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                .catch { Log.e("catchVMTickets", it.toString()) }
                .launchIn(viewModelScope)
        }
    }
}