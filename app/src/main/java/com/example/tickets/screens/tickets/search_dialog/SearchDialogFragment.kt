package com.example.tickets.screens.tickets.search_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.tickets.R
import com.example.tickets.databinding.FragmentSearchDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchDialogViewModel by viewModels()

    private var cityFrom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            cityFrom = it.getString(CITY_FROM)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f)

            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
                bottomSheet.let {
                    val behaviour = BottomSheetBehavior.from(it)
                    setupFullHeight(it)
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editFrom.setText(cityFrom)

        binding.istanbulContainer.setOnClickListener { binding.editTo.setText(binding.tvIstanbul.text) }
        binding.sochiContainer.setOnClickListener { binding.editTo.setText(binding.tvSochi.text) }
        binding.phuketContainer.setOnClickListener { binding.editTo.setText(binding.tvPhuket.text) }

        val navController = Navigation.findNavController(requireActivity(), R.id.nav_fragment)

        binding.hardWay.setOnClickListener {
            navController.navigate(R.id.action_tickets_fragment_to_hardWayFragment)
            dismiss()
        }

        binding.weekend.setOnClickListener {
            navController.navigate(R.id.action_tickets_fragment_to_weekendFragment)
            dismiss()
        }

        binding.hotTickets.setOnClickListener {
            navController.navigate(R.id.action_tickets_fragment_to_hotTicketsFragment)
            dismiss()
        }

        binding.anywhere.setOnClickListener {
            binding.editTo.setText(binding.tvAnywhere.text)
        }

        binding.clearCityTo.setOnClickListener {
            binding.editTo.setText("")
        }

        binding.search.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("cityFrom", binding.editFrom.text.toString())
            bundle.putString("cityTo", binding.editTo.text.toString())

            navController.navigate(
                R.id.ticketRecommendationsFragment,
                bundle
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CITY_FROM = "city_from"

        fun newInstance(cityFrom: String) =
            SearchDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(CITY_FROM, cityFrom)
                }
            }
    }
}