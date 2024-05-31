package com.example.tickets.screens.tickets.all_tickets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetTicketsUseCase
import com.example.tickets.adapter.DelegateAdapterItem
import com.example.tickets.model.TicketItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTicketsViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {
    private val _tickets = MutableLiveData<List<DelegateAdapterItem>>()
    val tickets: LiveData<List<DelegateAdapterItem>>
        get() = _tickets

    init {
        fetchTickets()
    }

    private fun fetchTickets() {
        viewModelScope.launch {
            getTicketsUseCase.execute()
                .onStart { Log.e("start", "") }
                .onEach { ticketsServer ->
                    _tickets.postValue(ticketsServer.map { ticketServer ->
                        TicketItem.ticketToTicketItem(ticketServer)
                    })
                }
                .catch { Log.e("catch", it.toString()) }
                .launchIn(viewModelScope)
        }
    }
}