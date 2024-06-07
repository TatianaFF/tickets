package com.example.tickets.screens.tickets.ticket_recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tickets.R
import com.example.tickets.databinding.FragmentTicketRecommendationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketRecommendationsFragment : Fragment() {

    private var _binding: FragmentTicketRecommendationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TicketRecommendationsViewModel by viewModels()

    private var cityFrom: String? = null
    private var cityTo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            cityFrom = it.getString(CITY_FROM)
            cityTo = it.getString(CITY_TO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketRecommendationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(requireActivity(), R.id.nav_fragment)

        initValues(navController)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.flights.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            binding.tvFlight1.text = it[0].title
            binding.tvPriceFlight1.text = it[0].price.value.toString()
            binding.tvTimesFlight1.text = it[0].timeRange.take(3).joinToString()

            binding.tvFlight2.text = it[1].title
            binding.tvPriceFlight2.text = it[1].price.value.toString()
            binding.tvTimesFlight2.text = it[1].timeRange.take(3).joinToString()

            binding.tvFlight3.text = it[2].title
            binding.tvPriceFlight3.text = it[2].price.value.toString()
            binding.tvTimesFlight3.text = it[2].timeRange.take(3).joinToString()
        })
    }

    private fun initValues(navController: NavController) {
        binding.editFrom.setText(cityFrom)
        binding.editTo.setText(cityTo)

        binding.arrowBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        binding.showAllTickets.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("cityFrom", binding.editFrom.text.toString())
            bundle.putString("cityTo", binding.editTo.text.toString())

            navController.navigate(
                R.id.allTicketsFragment,
                bundle
            )
        }

        binding.swap.setOnClickListener {
            binding.editFrom.text = binding.editTo.text.also { binding.editTo.text = binding.editFrom.text }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CITY_FROM = "cityFrom"
        private const val CITY_TO = "cityTo"
    }
}