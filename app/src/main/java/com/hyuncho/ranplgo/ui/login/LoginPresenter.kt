package com.hyuncho.ranplgo.ui.login

import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.hyuncho.ranplgo.R


class LoginPresenter : LoginContract.Presenter {

    private var loginView : LoginContract.View? = null

    private lateinit var googleSignInClient : GoogleSignInClient;
    private lateinit var firebaseAuth : FirebaseAuth

    companion object{
        private final var RC_SIGN_IN : Int = 9001;
    }

    override fun attachView(view: LoginContract.View) {
        loginView = view
    }

    override fun applyLogin() {
        val signInIntent = googleSignInClient?.getSignInIntent()
        loginView?.startLoginActivity(signInIntent, RC_SIGN_IN);

    }

    override fun verifyLogin(requestCode: Int, resultCode: Int, data: Intent?){
        //google login intent response
        if (requestCode === RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                loginView?.firebaseAuthWithGoogle(account, firebaseAuth)
            } catch (e: ApiException){

            }
        }
    }

    override fun configureGoogleSignIn(googleSignInClient : GoogleSignInClient, firebaseAuth: FirebaseAuth){
        this.googleSignInClient = googleSignInClient
        this.firebaseAuth=firebaseAuth

    }



    override fun detachView() {
        loginView = null
    }

}