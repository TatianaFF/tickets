package com.example.tickets.screens.tickets

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.tickets.R
import com.example.tickets.adapter.DelegateAdapterItem
import com.example.tickets.adapter.ItemSnapHelper
import com.example.tickets.adapter.MainCompositeAdapter
import com.example.tickets.adapter.OfferAdapterDelegate
import com.example.tickets.databinding.FragmentTicketsBinding
import com.example.tickets.screens.BaseFragment
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

//    private lateinit var dialog: SearchDialogFragment

    override fun getViewBinding() = FragmentTicketsBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValues()
        setFocusListenerEditTo()
        setupAdapter()
        observeViewModel()
    }

    private fun initValues() {
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment

        val navController = navHost.navController

        settings = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        binding.editFrom.setText(settings.getString(CITY_FROM, ""))

        binding.searchTickets.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(CITY_FROM, binding.editFrom.text.toString())
            bundle.putString(CITY_TO, binding.editTo.text.toString())

            navController.navigate(
                R.id.ticketRecommendationsFragment,
                bundle
            )
        }

        binding.includeHints.istanbulContainer.setOnClickListener { binding.editTo.setText(binding.includeHints.tvIstanbul.text) }
        binding.includeHints.sochiContainer.setOnClickListener { binding.editTo.setText(binding.includeHints.tvSochi.text) }
        binding.includeHints.phuketContainer.setOnClickListener { binding.editTo.setText(binding.includeHints.tvPhuket.text) }

        binding.includeHints.hardWay.setOnClickListener {
            navController.navigate(R.id.action_tickets_fragment_to_hardWayFragment)
        }

        binding.includeHints.weekend.setOnClickListener {
            navController.navigate(R.id.action_tickets_fragment_to_weekendFragment)
        }

        binding.includeHints.hotTickets.setOnClickListener {
            navController.navigate(R.id.action_tickets_fragment_to_hotTicketsFragment)
        }

        binding.includeHints.anywhere.setOnClickListener {
            binding.editTo.setText(binding.includeHints.tvAnywhere.text)
        }
    }

//    private fun setFocusListenerEditTo() {
//        binding.editTo.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
//            if (hasFocus) {
//                dialog = SearchDialogFragment.newInstance(binding.editFrom.text.toString())
//                dialog.show(this.parentFragmentManager, "tag")
//            }
//        }
//    }

    override fun onPause() {
        super.onPause()
        settings.edit().putString(CITY_FROM, binding.editFrom.text.toString()).apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setFocusListenerEditTo() {
        binding.editTo.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

                binding.includeHints.root.apply {
                    alpha = 0f

                    visibility = View.VISIBLE

                    animate()
                        .alpha(1f)
                        .setDuration(300)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                binding.scrollview.post(Runnable {
                                    binding.scrollview.smoothScrollTo(
                                        0,
                                        binding.titleContainer.height
                                    )
                                })
                            }
                        })
                }

            } else {
                binding.includeHints.root.apply {
                    animate()
                        .alpha(0f)
                        .setDuration(300)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                visibility = View.GONE
                            }
                        })
                }
            }
        }
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

    companion object {
        private const val CITY_FROM = "cityFrom"
        private const val CITY_TO = "cityTo"
    }
}