package com.ggd.zendee.feature.profile.screen

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentProfileBinding
import com.ggd.zendee.feature.profile.adapter.ProfileTabAdapter
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        viewPager.adapter = ProfileTabAdapter(parentFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        binding.btnSetting.setOnClickListener {
            val action = ProfileFragmentDirections.toSettingFragment()
            findNavController().navigate(action)
        }
    }

}