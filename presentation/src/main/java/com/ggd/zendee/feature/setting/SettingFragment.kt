package com.ggd.zendee.feature.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSettingBinding
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.ggd.zendee.utils.HiltApplication
import com.ggd.zendee.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private val requestImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
//        Glide.with(requireContext()).load(it).circleCrop().into(binding.ivProfile)
        // todo 1. 내정보 수정 기능만 사용하면 저절로 수정되니께
    }

    override fun start() {
        profileViewModel.getMyInfo()

        val context = requireContext()

        // todo : error
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        profileViewModel.myInfo.observe(viewLifecycleOwner) {
            with(binding) {
                Glide.with(requireContext()).load(it.image).circleCrop().into(ivProfile)
                tvUserNick.text = it.nick
            }
        }

        with(binding) {
            btnSetUserProfile.setOnClickListener {
                requestImage.launch("image/*")
            }
            btnSecurity.setOnClickListener {
                //
                makeToast(context, "약관 및 개인정보 처리 동의")
                Log.d("Token", "my Token : ${HiltApplication.prefs.accessToken}")
                viewModel.getMyLikeContent()
            }
            btnAppVersion.setOnClickListener {
                makeToast(context, "앱 버전")
            }
            btnLogout.setOnClickListener {
                makeToast(context, "로그아웃 되었습니다.")
                HiltApplication.prefs.deleteToken()
                val action = SettingFragmentDirections.toStartFragment()
                findNavController().navigate(action)
            }
        }
    }

}