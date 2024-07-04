package com.example.tickets.screens.tickets.hints

import android.os.Bundle
import android.view.View
import com.example.tickets.databinding.FragmentHardWayBinding
import com.example.tickets.screens.BaseFragment

class HardWayFragment : BaseFragment<FragmentHardWayBinding>() {

    override fun getViewBinding() = FragmentHardWayBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}