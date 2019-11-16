package com.hyuncho.ranplgo.ui.result


import android.os.Bundle
import android.widget.Toast
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.ui.base.BaseActivity

class ResultActivity : BaseActivity(),
    ResultContract.View {

    private lateinit var resultPresenter: ResultPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()


        resultPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        resultPresenter.detachView()
    }

    override fun initPresenter() {
        resultPresenter = ResultPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
    }

}
