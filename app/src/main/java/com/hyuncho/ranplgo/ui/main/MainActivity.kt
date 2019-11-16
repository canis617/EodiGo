package com.hyuncho.ranplgo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.service.firebase.FireBaseDB
import com.hyuncho.ranplgo.service.firebase.GoogleLogin
import com.hyuncho.ranplgo.ui.base.BaseActivity

class MainActivity : BaseActivity(),
    MainContract.View {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var firebaseDB : FireBaseDB
    private lateinit var googleLogin : GoogleLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()

        googleLogin = GoogleLogin()
        firebaseDB = FireBaseDB(googleLogin.getUserId())

        firebaseDB.updateResult()

        mainPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }

    override fun initPresenter() {
        mainPresenter = MainPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
    }

}
