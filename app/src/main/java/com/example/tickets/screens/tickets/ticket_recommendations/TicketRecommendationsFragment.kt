package com.example.tickets.screens.tickets.ticket_recommendations

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tickets.R
import com.example.tickets.databinding.FragmentTicketRecommendationsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(requireActivity(), R.id.nav_fragment)

        initValues(navController)
        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModel.flights.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            binding.tvFlight1.text = it[0].title
            binding.tvPriceFlight1.text = NumberFormat.getInstance(Locale("ru")).format(it[0].price.value) + " "
            binding.tvTimesFlight1.text = it[0].timeRange.take(3).joinToString(" ")

            binding.tvFlight2.text = it[1].title
            binding.tvPriceFlight2.text = NumberFormat.getInstance(Locale("ru")).format(it[1].price.value) + " "
            binding.tvTimesFlight2.text = it[1].timeRange.take(3).joinToString(" ")

            binding.tvFlight3.text = it[2].title
            binding.tvPriceFlight3.text = NumberFormat.getInstance(Locale("ru")).format(it[2].price.value) + " "
            binding.tvTimesFlight3.text = it[2].timeRange.take(3).joinToString(" ")
        })
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initValues(navController: NavController) {
        binding.editFrom.setText(cityFrom)
        binding.editTo.setText(cityTo)

        binding.arrowBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        binding.showAllTickets.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(CITY_FROM, binding.editFrom.text.toString())
            bundle.putString(CITY_TO, binding.editTo.text.toString())
            bundle.putString(DATE_TO, viewModel.dateTo.value)

            navController.navigate(
                R.id.allTicketsFragment,
                bundle
            )
        }

        binding.swap.setOnClickListener {
            binding.editFrom.text = binding.editTo.text.also { binding.editTo.text = binding.editFrom.text }
        }

        binding.tvDayMonth.text = getDateMonth()
        binding.tvDayWeek.text = getDayWeek()

        binding.cardDateTo.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val daysOfWeekNames = arrayOf(
                "вс",
                "пн",
                "вт",
                "ср",
                "чт",
                "пт",
                "сб"
            )

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.apply {
                        set(year, monthOfYear, dayOfMonth)
                    }
                    val monthNew = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale("ru"))
                    binding.tvDayMonth.text = dayOfMonth.toString() + " " + monthNew.take(3)

                    binding.tvDayWeek.text = daysOfWeekNames[calendar[Calendar.DAY_OF_WEEK]-1]

                    viewModel.setDateTo("$dayOfMonth $monthNew")
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        binding.cardDateFrom.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->

                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateMonth(): String {
        val calendar = Calendar.getInstance()

        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val month = calendar.getDisplayName(
            Calendar.MONTH,
            Calendar.LONG_FORMAT, Locale("ru")
        )

        viewModel.setDateTo("$day $month")

        return day.toString() + " " + month.take(3) + " "
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDayWeek(): String {
        val calendar = Calendar.getInstance()

        val daysOfWeekNames = arrayOf(
            "вс",
            "пн",
            "вт",
            "ср",
            "чт",
            "пт",
            "сб"
        )

        val dayWeek = daysOfWeekNames[calendar[Calendar.DAY_OF_WEEK]-1]

        return "$dayWeek "
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