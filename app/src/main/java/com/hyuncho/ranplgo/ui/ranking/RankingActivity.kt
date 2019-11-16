package com.hyuncho.ranplgo.ui.ranking


import android.os.Bundle
import android.widget.Toast
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.ui.base.BaseActivity

class RankingActivity : BaseActivity(),
    RankingContract.View {

    private lateinit var rankingPresenter: RankingPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()


        rankingPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        rankingPresenter.detachView()
    }

    override fun initPresenter() {
        rankingPresenter = RankingPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@RankingActivity, error, Toast.LENGTH_SHORT).show()
    }

}
