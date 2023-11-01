package com.ggd.zendee.feature.profile.screen

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
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

    private val tabTitleArray = arrayOf(
        "좋아요", "싫어요", "이슈", "댓글"
    )

    override fun start() {
        viewModel.getMyInfo()

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        viewPager.adapter = ProfileTabAdapter(parentFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        viewModel.myInfo.observe(viewLifecycleOwner) {
            with(binding) {
                Glide.with(requireContext()).load(it.image).circleCrop().into(ivProfile)
                tvUserNick.text = it.nick
            }
        }


        binding.btnSetting.setOnClickListener {
            val action = ProfileFragmentDirections.toSettingFragment()
            findNavController().navigate(action)
        }
    }

}