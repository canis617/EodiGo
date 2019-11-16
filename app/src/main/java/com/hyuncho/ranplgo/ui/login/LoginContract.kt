package com.hyuncho.ranplgo.ui.login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.hyuncho.ranplgo.ui.base.BasePresenter
import com.hyuncho.ranplgo.ui.base.BaseView

interface LoginContract {
    interface View : BaseView {
        fun startLoginActivity(signInIntent: Intent, RC_SIGN_IN: Int)
        fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?, firebaseAuth: FirebaseAuth)
    }

    interface Presenter :
        BasePresenter<View> {
        fun applyLogin()
        fun configureGoogleSignIn(googleSignInClient : GoogleSignInClient, firebaseAuth: FirebaseAuth)
        fun verifyLogin(requestCode: Int, resultCode: Int, data: Intent?)
    }
}