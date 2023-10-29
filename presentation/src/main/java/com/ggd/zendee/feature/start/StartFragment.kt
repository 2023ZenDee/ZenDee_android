package com.ggd.zendee.feature.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ggd.model.oauth.GoogleOauthRequestModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentStartBinding
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.utils.GOOGLE_CLIENT_ID
import com.ggd.zendee.utils.HiltApplication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel>(R.layout.fragment_start) {

    override val viewModel: StartViewModel by viewModels()

    private lateinit var account: GoogleSignInAccount
    private lateinit var nick: String
    private lateinit var email: String

    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        kotlin.runCatching {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            account = task.getResult(ApiException::class.java)

            nick = account.displayName.toString()
            email = account.email.toString()
        }.onSuccess {
            viewModel.googleOauthLogin(
                GoogleOauthRequestModel(email, nick)
            )
            Log.d(TAG, "Google Oauth Success!! $nick, $email")
            Toast.makeText(requireContext(), "로그인이 되었습니다", Toast.LENGTH_SHORT).show()
        }.onFailure { e ->
            Log.e(TAG, "Google Oauth Failed.." + e.stackTraceToString())
            Toast.makeText(requireContext(), "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun start() {
        /** 자동 로그인 */
        if (HiltApplication.prefs.autoLogin) {
            findNavController().navigate(R.id.action_startFragment_to_mapFragment)
        }

        (activity as MainActivity).handleBottomNavigation(false)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_loginFragment)
//            findNavController().navigate(R.id.mapFragment)
        }

        /** Google Oauth */
        binding.btnGoogle.setOnClickListener {
            val googleOauth = GoogleOauth(requireActivity(), googleAuthLauncher)
            googleOauth.requestGoogleLogin()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.oauthLoginState.collect { state ->
                if (state.isSuccess) {
                    findNavController().navigate(R.id.action_startFragment_to_mapFragment)
                }
                if (state.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvJoin.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_signupNickFragment)
        }
    }

    companion object {
        const val TAG = "StartFragment"
    }

}