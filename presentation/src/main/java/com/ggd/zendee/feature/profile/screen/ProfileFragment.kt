package com.ggd.zendee.feature.profile.screen

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ggd.model.user.FixedInfo
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentProfileBinding
import com.ggd.zendee.feature.profile.adapter.ProfileTabAdapter
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.ggd.zendee.feature.setting.SettingFragment
import com.ggd.zendee.utils.bitmapToMultipart
import com.ggd.zendee.utils.uriToBitmap
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.http.Url
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel by viewModels()

    private val tabTitleArray = arrayOf(
        "좋아요", "싫어요", "이슈", "댓글"
    )

    private val requestImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        val imagePath = it?.uriToBitmap(requireContext())?.bitmapToMultipart()
        Log.i(TAG, "imageFile : $imagePath")

        viewModel.editMyInfo(
            imagePath,
            viewModel.myInfo.value!!.nick
        )
    }

    override fun start() {
        viewModel.getMyInfo()
        setTapRow()

        viewModel.myInfo.observe(viewLifecycleOwner) {
            with(binding) {
                val sampleImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/340px-Default_pfp.svg.png"
                if (it.image == null) {
                    Glide.with(requireContext()).load(sampleImage).circleCrop().into(ivProfile)
                } else {
                    Glide.with(requireContext()).load(it.image).circleCrop().into(ivProfile)
                }
                tvUserNick.text = it.nick
            }
        }

        with(binding) {
            btnSetProfile.setOnClickListener {
                requestImage.launch("image/*")
            }
            btnSetting.setOnClickListener {
                val action = ProfileFragmentDirections.toSettingFragment()
                findNavController().navigate(action)
            }
        }

    }

    private fun setTapRow() {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        viewPager.adapter = ProfileTabAdapter(parentFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    companion object {
        private const val TAG = "ProfileFragment"
    }

}