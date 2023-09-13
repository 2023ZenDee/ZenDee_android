package com.ggd.zendee.feature.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentStartBinding
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.utils.HiltApplication
import com.ggd.zendee.utils.clientId
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel>(R.layout.fragment_start) {

    override val viewModel: StartViewModel by viewModels()

    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        Log.d(TAG, "task: $task")

        kotlin.runCatching {
            task.getResult(ApiException::class.java)
        }.onSuccess { account ->
            val userName = account.givenName
            val serverAuth = account.serverAuthCode
            Log.d(TAG, "userName : $userName, serverAuth : $serverAuth")

            // todo : 이동 코드 짜기
        }.onFailure { e ->
            Log.e(TAG, e.stackTraceToString())
        }
    }

    override fun start() {
        if (HiltApplication.prefs.autoLogin) {
            findNavController().navigate(R.id.action_startFragment_to_mapFragment)
        }

        (activity as MainActivity).handleBottomNavigation(false)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }

        binding.btnGoogle.setOnClickListener {
            requestGoogleLogin()
        }

        binding.tvJoin.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_signupNickFragment)
        }
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(clientId)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(), googleSignInOption)
    }

    companion object {
        const val TAG = "StartFragment"
    }

}