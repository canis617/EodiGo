package com.hyuncho.ranplgo.ui.ranking


import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.data.user.ScoreAdapter
import com.hyuncho.ranplgo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ranking.*

class RankingActivity : BaseActivity(),
    RankingContract.View {

    private lateinit var rankingPresenter: RankingPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        initPresenter()

        rankingPresenter.attachView(this)

        rankingPresenter.setStatus(this, "guesser")


        ranking_guesser_btn.setOnClickListener {
            rankingPresenter.setStatus(this, "guesser")
        }
        ranking_visitor_btn.setOnClickListener {
            rankingPresenter.setStatus(this, "visitor")
        }
        ranking_tracking_btn.setOnClickListener {
            rankingPresenter.setStatus(this, "tracking")
        }

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

    override fun setRecyclerView(scoreAdapter : ScoreAdapter){
        ranking_recycler_view?.adapter = scoreAdapter
        ranking_recycler_view?.layoutManager = LinearLayoutManager(this)
        ranking_recycler_view?.setHasFixedSize(true)
    }

    override fun setDisplay(status: String) {
        ranking_display.setText(status)
    }


}
