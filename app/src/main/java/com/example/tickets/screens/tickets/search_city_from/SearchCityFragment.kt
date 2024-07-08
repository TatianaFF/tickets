package com.example.tickets.screens.tickets.search_city_from

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tickets.R
import com.example.tickets.databinding.FragmentSearchCityBinding
import com.example.tickets.screens.BaseFragment
import com.example.tickets.utils.CITY_FROM
import com.example.tickets.utils.CITY_TO
import com.example.tickets.utils.SETTINGS
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchCityFragment : BaseFragment<FragmentSearchCityBinding>() {

    private val viewModel: SearchCityViewModel by viewModels()

    private lateinit var settings: SharedPreferences

    private lateinit var cityTv: String
    override fun getViewBinding() = FragmentSearchCityBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            cityTv = requireNotNull(it.getString(CITY_FROM) ?: it.getString(CITY_TO))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValues()
    }

    private fun initValues() {
        binding.arrowBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment

        val navController = navHost.navController

        if (cityTv == CITY_TO) {
            binding.lv.visibility = View.GONE
            binding.includeHints.root.visibility = View.VISIBLE
            initHints(navController)
        }

        settings = requireContext().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        binding.sv.requestFocus()
        binding.sv.setQuery(settings.getString(cityTv, ""), true)

        val listCities = arrayListOf(
            "Москва",
            "Санкт-Петербург",
            "Новосибирск",
            "Екатеринбург",
            "Казань",
            "Красноярск",
            "Нижний Новгород",
            "Челябинск",
            "Уфа",
            "Самара",
            "Ростов-на-Дону",
            "Краснодар",
            "Омск",
            "Воронеж",
            "Пермь",
            "Волгоград"
        )

        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            listCities
        )

        binding.lv.adapter = adapter
        binding.lv.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val selectedCity = adapter.getItem(position)
            settings.edit().putString(cityTv, selectedCity).apply()

            onNavigate(navController)
        }

        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (listCities.contains(query)) {
                    adapter.filter.filter(query)
                } else {
                    Toast.makeText(requireContext(), "Город не найден", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (cityTv == CITY_TO) {
                    if (binding.sv.query.isBlank()) {
                        binding.lv.visibility = View.GONE
                        binding.includeHints.root.visibility = View.VISIBLE
                    } else {
                        binding.lv.visibility = View.VISIBLE
                        binding.includeHints.root.visibility = View.GONE
                    }
                }
                adapter.filter.filter(newText)
                return false
            }
        })

    }

    private fun initHints(navController: NavController) {
        binding.includeHints.istanbulContainer.setOnClickListener {
            settings.edit().putString(cityTv, binding.includeHints.tvIstanbul.text.toString()).apply()
            onNavigate(navController)
        }
        binding.includeHints.sochiContainer.setOnClickListener {
            settings.edit().putString(cityTv, binding.includeHints.tvSochi.text.toString()).apply()
            onNavigate(navController)
        }
        binding.includeHints.phuketContainer.setOnClickListener {
            settings.edit().putString(cityTv, binding.includeHints.tvPhuket.text.toString()).apply()
            onNavigate(navController)
        }

        binding.includeHints.hardWay.setOnClickListener {
            navController.navigate(R.id.hardWayFragment)
        }

        binding.includeHints.weekend.setOnClickListener {
            navController.navigate(R.id.weekendFragment)
        }

        binding.includeHints.hotTickets.setOnClickListener {
            navController.navigate(R.id.hotTicketsFragment)
        }

        binding.includeHints.anywhere.setOnClickListener {
            settings.edit().putString(cityTv, binding.includeHints.tvAnywhere.text.toString()).apply()
            onNavigate(navController)
        }
    }

    private fun onNavigate(navController: NavController) {
        if ((cityTv == CITY_TO && settings.getString(CITY_FROM, "")?.isNotBlank() == true) || (cityTv == CITY_FROM && settings.getString(CITY_TO, "")?.isNotBlank() == true))
            navController.navigate(R.id.ticketRecommendationsFragment)
        else
            navController.navigate(R.id.tickets_fragment)
    }
}