package com.example.tickets.screens.tickets

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.graphics.alpha
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.animate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.tickets.R
import com.example.tickets.adapter.MainCompositeAdapter
import com.example.tickets.adapter.OfferAdapterDelegate
import com.example.tickets.databinding.FragmentTicketsBinding
import com.example.tickets.screens.tickets.search_dialog.SearchDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TicketsFragment : Fragment() {

    private var _binding: FragmentTicketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TicketsViewModel by viewModels()

    private val compositeAdapter by lazy {
        MainCompositeAdapter.Builder()
            .add(OfferAdapterDelegate())
            .build()
    }

    private lateinit var settings: SharedPreferences

    private lateinit var dialog: SearchDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextWatcherEditFrom()
        setFocusListenerEditTo()

        setupAdapter()
        observeViewModel()
        initValues()
    }

    private fun initValues() {

        val navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment

        val navController = navHost.navController

        settings = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        binding.editFrom.setText(settings.getString(CITY_FROM, ""))

        binding.searchTickets.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("cityFrom", binding.editFrom.text.toString())
            bundle.putString("cityTo", binding.editTo.text.toString())

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

//        binding.includeHints.clearCityTo.setOnClickListener {
//            binding.editTo.setText("")
//        }
    }

    private fun setTextWatcherEditFrom() {
        binding.editFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                settings.edit().putString(CITY_FROM, s.toString()).apply()
            }

        })
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
    }

    private fun observeViewModel() {
        viewModel.offers.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            compositeAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CITY_FROM = "CityFrom"
    }
}