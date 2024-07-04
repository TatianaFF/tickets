package com.example.tickets.screens.hotels

import com.example.tickets.databinding.FragmentHotelsBinding
import com.example.tickets.screens.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelsFragment : BaseFragment<FragmentHotelsBinding>() {

    override fun getViewBinding() = FragmentHotelsBinding.inflate(layoutInflater)
}