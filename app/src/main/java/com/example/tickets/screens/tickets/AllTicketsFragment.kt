package com.example.tickets.screens.tickets

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tickets.R
import com.example.tickets.databinding.FragmentAllTicketsBinding
import com.example.tickets.databinding.FragmentHardWayBinding
import dagger.hilt.android.AndroidEntryPoint

class AllTicketsFragment : Fragment() {

    private var _binding: FragmentAllTicketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllTicketsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllTicketsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}