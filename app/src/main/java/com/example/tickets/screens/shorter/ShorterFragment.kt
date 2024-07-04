package com.example.tickets.screens.shorter

import com.example.tickets.databinding.FragmentShorterBinding
import com.example.tickets.screens.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShorterFragment : BaseFragment<FragmentShorterBinding>() {

    override fun getViewBinding() = FragmentShorterBinding.inflate(layoutInflater)

}