/* For Google Login change code from mvc to mvp architecture
*  https://firebase.google.com/docs/auth/android/google-signin?hl=ko
* */

package com.hyuncho.ranplgo.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.ui.base.BaseActivity
import com.hyuncho.ranplgo.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(),
    LoginContract.View {

    private lateinit var loginPresenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initPresenter()

        loginPresenter.attachView(this)

        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        val firebaseAuth = FirebaseAuth.getInstance()
        loginPresenter.configureGoogleSignIn(googleSignInClient, firebaseAuth)

        btn_googleSignIn.setOnClickListener {
            loginPresenter.applyLogin()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        loginPresenter.verifyLogin(requestCode, resultCode, data)
    }

    override fun startLoginActivity(signInIntent: Intent, RC_SIGN_IN: Int){
        startActivityForResult(
            signInIntent,
            RC_SIGN_IN
        );
    }

    override fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?, firebaseAuth:FirebaseAuth) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        firebaseAuth!!.signInWithCredential((credential))
            .addOnCompleteListener(this) {
                //success?
                if(it.isSuccessful) {
                    val user = firebaseAuth?.currentUser
                    Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "login fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.detachView()
    }

    override fun initPresenter() {
        loginPresenter = LoginPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@LoginActivity, error, Toast.LENGTH_SHORT).show()
    }

}
