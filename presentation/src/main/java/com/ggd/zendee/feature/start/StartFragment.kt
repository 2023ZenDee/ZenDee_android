package com.ggd.zendee.feature.start

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ggd.model.oauth.GoogleOauthRequestModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentStartBinding
import com.ggd.zendee.utils.GOOGLE_CLIENT_ID
import com.ggd.zendee.utils.HiltApplication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel>(R.layout.fragment_start) {

    override val viewModel: StartViewModel by viewModels()

    private lateinit var account: GoogleSignInAccount
    private lateinit var nick: String
    private lateinit var email: String
    private lateinit var code: String

//    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val intent =  printIntent(result.data)
//        Log.d(TAG, "result: $intent")
//        kotlin.runCatching {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            account = task.getResult(ApiException::class.java)
//
//            nick = account.displayName.toString()
//            email = account.email.toString()
//            code = account.serverAuthCode.toString()
//            Log.i(TAG, "nick: ${nick}, email: ${email}, code: $code")
//        }.onSuccess {
//            viewModel.googleOauthLogin(
//                GoogleOauthRequestModel(email, nick)
//            )
//            Log.d(TAG, "Google Oauth Success!! $nick, $email")
//            Toast.makeText(requireContext(), "로그인이 되었습니다", Toast.LENGTH_SHORT).show()
//        }.onFailure { e ->
//            Log.e(TAG, "Google Oauth Failed.." + e.stackTraceToString())
//            Toast.makeText(requireContext(), "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun start() {
        if (HiltApplication.prefs.autoLogin) { findNavController().navigate(R.id.action_startFragment_to_mapFragment) }

        buttonsHandling()

        loginEventHandling()
    }

    companion object {
        const val TAG = "StartFragment"
    }

//    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
//
//
//    private fun requestGoogleLogin() {
//        googleSignInClient.signOut()
//        val signInIntent = googleSignInClient.signInIntent
//        googleAuthLauncher.launch(signInIntent)
//    }

//    private fun getGoogleClient(): GoogleSignInClient {
//        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub")) // todo ?
////            .requestServerAuthCode(GOOGLE_CLIENT_ID)
//            .requestEmail()
//            .build()
//
//        return GoogleSignIn.getClient(requireActivity(), googleSignInOption)
//    }

    private fun buttonsHandling() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }
//        binding.btnGoogle.setOnClickListener {
//            requestGoogleLogin()
//        }
        binding.tvJoin.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_signupNickFragment)
        }
    }

    private fun loginEventHandling() {
        lifecycleScope.launch {
            viewModel.oauthLoginState.collect { state ->
                if (state.isSuccess) {
                    findNavController().navigate(R.id.action_startFragment_to_mapFragment)
                }
                if (state.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

// SHA1: DF:C3:91:7A:AD:CD:82:4B:C0:8E:9C:8D:24:20:E9:7C:8C:EE:19:F5
// SHA1: DF:C3:91:7A:AD:CD:82:4B:C0:8E:9C:8D:24:20:E9:7C:8C:EE:19:F5
// SHA1: DF:C3:91:7A:AD:CD:82:4B:C0:8E:9C:8D:24:20:E9:7C:8C:EE:19:F5