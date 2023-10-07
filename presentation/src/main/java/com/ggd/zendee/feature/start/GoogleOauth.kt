package com.ggd.zendee.feature.start

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.LogPrinter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.ggd.zendee.utils.GOOGLE_CLIENT_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.qualifiers.ActivityContext

class GoogleOauth(
    private val activity: Activity,
    private val googleAuthLauncher: ActivityResultLauncher<Intent>
) {

    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }

    fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
//            .requestServerAuthCode(GOOGLE_CLIENT_ID)
            .requestEmail() // 이메일도 요청할 수 있다.
            .build()

        return GoogleSignIn.getClient(activity, googleSignInOption)
    }
}

