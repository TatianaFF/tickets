package com.example.tickets.screens.subscriptions

import com.example.tickets.databinding.FragmentSubscriptionsBinding
import com.example.tickets.screens.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionsFragment : BaseFragment<FragmentSubscriptionsBinding>() {

    override fun getViewBinding() = FragmentSubscriptionsBinding.inflate(layoutInflater)

}