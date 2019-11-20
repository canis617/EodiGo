package com.hyuncho.ranplgo.ui.main

import android.os.Bundle
import android.widget.Toast
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),
    MainContract.View {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()

        mainPresenter.attachView(this)

        main_start_btn.setOnClickListener {
            mainPresenter.openMenuSelect(this)
        }
        main_rank_btn.setOnClickListener {
            mainPresenter.gotoRanking(this)
        }
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
