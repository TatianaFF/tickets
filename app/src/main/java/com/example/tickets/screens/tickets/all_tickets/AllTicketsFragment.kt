package com.example.tickets.screens.tickets.all_tickets

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tickets.adapter.MainCompositeAdapter
import com.example.tickets.adapter.TicketAdapterDelegate
import com.example.tickets.databinding.FragmentAllTicketsBinding
import com.example.tickets.screens.BaseFragment
import com.example.tickets.utils.CITY_FROM
import com.example.tickets.utils.CITY_TO
import com.example.tickets.utils.DATE_TO
import com.example.tickets.utils.SETTINGS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTicketsFragment : BaseFragment<FragmentAllTicketsBinding>() {

    private val viewModel: AllTicketsViewModel by viewModels()

    private lateinit var settings: SharedPreferences

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
        settings = requireContext().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        cityFrom = settings.getString(CITY_FROM, "")
        cityTo = settings.getString(CITY_TO, "")

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
}