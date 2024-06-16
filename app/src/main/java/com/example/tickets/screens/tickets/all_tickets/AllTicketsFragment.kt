package com.example.tickets.screens.tickets.all_tickets

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tickets.databinding.FragmentAllTicketsBinding
import androidx.lifecycle.Observer
import com.example.tickets.adapter.MainCompositeAdapter
import com.example.tickets.adapter.OfferAdapterDelegate
import com.example.tickets.adapter.TicketAdapterDelegate
import com.example.tickets.screens.tickets.TicketsFragment
import com.example.tickets.screens.tickets.ticket_recommendations.TicketRecommendationsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTicketsFragment : Fragment() {

    private var _binding: FragmentAllTicketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllTicketsViewModel by viewModels()

    private var cityFrom: String? = null
    private var cityTo: String? = null
    private var dateTo: String? = null

    private val compositeAdapter by lazy {
        MainCompositeAdapter.Builder()
            .add(TicketAdapterDelegate())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            cityFrom = it.getString(CITY_FROM)
            cityTo = it.getString(CITY_TO)
            dateTo = it.getString(DATE_TO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllTicketsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValues()
        observeViewModel()
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerTickets.adapter = compositeAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun initValues() {
        binding.tvFrom.text = cityFrom
        binding.tvTo.text = cityTo
        binding.tvDateTo.text = "$dateTo "

        binding.arrowBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
    }

    private fun observeViewModel() {
        viewModel.tickets.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            compositeAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CITY_FROM = "cityFrom"
        private const val CITY_TO = "cityTo"
        private const val DATE_TO = "dateTo"
    }
}