package com.example.tickets.screens.tickets.ticket_recommendations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetRecommendedFlightsUseCase
import com.example.models.Flight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketRecommendationsViewModel  @Inject constructor(
    private val getOffersUseCase: GetRecommendedFlightsUseCase
) : ViewModel() {
    private val _flights = MutableLiveData<List<Flight>>()
    val flights: LiveData<List<Flight>>
        get() = _flights

    init {
        fetchFlights()
    }

    private fun fetchFlights() {
        viewModelScope.launch {
            getOffersUseCase.execute()
                .onStart { Log.e("start", "") }
                .onEach { flightsServer ->
                    _flights.postValue(flightsServer)
                }
                .catch { Log.e("catch", it.toString()) }
                .launchIn(viewModelScope)
        }
    }
}