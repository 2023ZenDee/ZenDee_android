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
}

