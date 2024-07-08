package com.example.tickets.screens.tickets

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tickets.R
import com.example.tickets.adapter.DelegateAdapterItem
import com.example.tickets.adapter.ItemSnapHelper
import com.example.tickets.adapter.MainCompositeAdapter
import com.example.tickets.adapter.OfferAdapterDelegate
import com.example.tickets.databinding.FragmentTicketsBinding
import com.example.tickets.screens.BaseFragment
import com.example.tickets.utils.CITY_FROM
import com.example.tickets.utils.CITY_TO
import com.example.tickets.utils.SETTINGS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketsFragment : BaseFragment<FragmentTicketsBinding>() {
    private val viewModel: TicketsViewModel by viewModels()

    private val compositeAdapter by lazy {
        MainCompositeAdapter.Builder()
            .add(OfferAdapterDelegate())
            .build()
    }

    private lateinit var settings: SharedPreferences

    override fun getViewBinding() = FragmentTicketsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = requireContext().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        settings.edit()
            .putString(CITY_TO, "")
            .apply()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValues()
        setupAdapter()
        observeViewModel()
    }

    private fun initValues() {
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        val navController = navHost.navController

        binding.tvFrom.text = settings.getString(CITY_FROM, "")
        binding.tvTo.text = settings.getString(CITY_TO, "")

        binding.tvFrom.setOnClickListener { onClickTvFrom(navController) }
        binding.tvTo.setOnClickListener { onClickTvTo(navController) }
    }

    private fun onClickTvFrom(navController: NavController) {
        val bundle = Bundle()
        bundle.putString(CITY_FROM, CITY_FROM)

        navController.navigate(
            R.id.searchCityFragment,
            bundle
        )
    }

    private fun onClickTvTo(navController: NavController) {
        val bundle = Bundle()
        bundle.putString(CITY_TO, CITY_TO)

        navController.navigate(
            R.id.searchCityFragment,
            bundle
        )
    }

    private fun setupAdapter() {
        binding.recyclerOffers.adapter = compositeAdapter
        ItemSnapHelper().attachToRecyclerView(binding.recyclerOffers)
    }

    private fun observeViewModel() {
        viewModel.offers.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            val list: List<DelegateAdapterItem> = it + it
            compositeAdapter.submitList(list)
        })
    }
}