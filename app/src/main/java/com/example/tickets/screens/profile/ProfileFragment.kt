package com.example.tickets.screens.profile

import com.example.tickets.databinding.FragmentProfileBinding
import com.example.tickets.screens.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun getViewBinding() = FragmentProfileBinding.inflate(layoutInflater)
}