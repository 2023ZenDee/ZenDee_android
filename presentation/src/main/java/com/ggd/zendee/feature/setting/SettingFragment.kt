package com.ggd.zendee.feature.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSettingBinding
import com.ggd.zendee.utils.HiltApplication
import com.ggd.zendee.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by viewModels()

    override fun start() {
        val context = requireContext()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        with(binding) {
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