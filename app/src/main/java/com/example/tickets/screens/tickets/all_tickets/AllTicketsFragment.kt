package com.example.tickets.screens.tickets.all_tickets

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tickets.adapter.MainCompositeAdapter
import com.example.tickets.adapter.TicketAdapterDelegate
import com.example.tickets.databinding.FragmentAllTicketsBinding
import com.example.tickets.screens.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTicketsFragment : BaseFragment<FragmentAllTicketsBinding>() {

    private val viewModel: AllTicketsViewModel by viewModels()

    private var cityFrom: String? = null
    private var cityTo: String? = null
    private var dateTo: String? = null

    private val compositeAdapter by lazy {
        MainCompositeAdapter.Builder()
            .add(TicketAdapterDelegate())
            .build()
    }

    override fun getViewBinding() = FragmentAllTicketsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            cityFrom = it.getString(CITY_FROM)
            cityTo = it.getString(CITY_TO)
            dateTo = it.getString(DATE_TO)
        }
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

    companion object {
        private const val CITY_FROM = "cityFrom"
        private const val CITY_TO = "cityTo"
        private const val DATE_TO = "dateTo"
    }
}