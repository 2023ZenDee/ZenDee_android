package com.ggd.zendee.feature.setting

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ggd.model.user.FixedInfo
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSettingBinding
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.ggd.zendee.utils.HiltApplication
import com.ggd.zendee.utils.bitmapToMultipart
import com.ggd.zendee.utils.makeToast
import com.ggd.zendee.utils.uriToBitmap
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.File


@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var context: Context

    private val requestImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        val imagePath = it?.uriToBitmap(requireContext())?.bitmapToMultipart()
        Log.i(TAG, "imageFile : $imagePath")

        profileViewModel.editMyImage(imagePath)
    }

    override fun start() {
        profileViewModel.getMyInfo()
        context = requireContext()

        observeMyInfo()

        buttonsHandling()
    }

    companion object {
        private const val TAG = "SettingFragment"
    }

    private fun observeMyInfo() {
        profileViewModel.myImage.observe(viewLifecycleOwner) {
            with(binding) {
                val sampleImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/340px-Default_pfp.svg.png"
                if (it != null) {
                    Glide.with(requireContext()).load(it).circleCrop().into(ivProfile)
                } else {
                    Glide.with(requireContext()).load(sampleImage).circleCrop().into(ivProfile)
                }
            }
        }
        profileViewModel.myNick.observe(viewLifecycleOwner) {
            binding.tvUserNick.text = it
        }
    }

    private fun buttonsHandling() {
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() } // todo : 이건 왜 이럼
            btnSetUserProfile.setOnClickListener { requestImage.launch("image/*") }
            btnSecurity.setOnClickListener { makeToast(context, "약관 및 개인정보 처리 동의") }
            btnAppVersion.setOnClickListener { makeToast(context, "앱 버전") }
            btnLogout.setOnClickListener {
                makeToast(context, "로그아웃 되었습니다.")
                HiltApplication.prefs.deleteToken()
                val action = SettingFragmentDirections.toStartFragment()
                findNavController().navigate(action)
            }
        }
    }


}