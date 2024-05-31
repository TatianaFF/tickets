package com.example.domain

import android.telecom.Call
import android.util.Log
import com.example.data.repository.OffersRepository
import com.example.models.Offer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(
    private val offersRepository: OffersRepository
) {
    fun execute(): Flow<List<Offer>> = offersRepository.getOffers()
}