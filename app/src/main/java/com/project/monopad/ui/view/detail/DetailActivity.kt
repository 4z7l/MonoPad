package com.project.monopad.ui.view.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.youtube.player.internal.v
import com.project.monopad.R
import com.project.monopad.databinding.ActivityDetailBinding
import com.project.monopad.ui.adapter.*
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.view.review.ImageSelectActivity
import com.project.monopad.ui.viewmodel.DetailViewModel
import com.project.monopad.util.DetailParsingUtil
import com.project.monopad.util.OtherMovieCase
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_detail

    override val viewModel: DetailViewModel by viewModel()

    /* start activity */
    override fun initStartView() {
        toolbarLayoutSetting()
        recyclerViewSetting()
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        viewModel.getDetailData(intent?.getIntExtra("movie_id", 89501) ?: 89501)
    }

    override fun initAfterBinding() {
        observeMovieDetailData()
        observeMovieCrewData()
        observeMovieCasterData()
        observeSimilarMovieData()
        observeRecommendMovieData()
        observeTrailerMovieData()
    }

    /* observe */
    private fun observeMovieDetailData(){
        viewModel.movieDetailData.observe(this, {
            toolbar_layout.title = it.title
            tv_detail_release_date.text = DetailParsingUtil.releaseDateParsing(it.release_date)
            tv_detail_runtime.text = DetailParsingUtil.runtimeParsing(it.runtime)
            tv_detail_genre.text = DetailParsingUtil.genreParsing(it.genres)
            tv_detail_overview.text = it.overview
        })

    }

    private fun observeMovieCrewData(){
        viewModel.movieCrewData.observe(this, {
            tv_detail_director.text = DetailParsingUtil.directorParsing(it)
        })
    }

    private fun observeMovieCasterData(){
        val casterAdapter = CasterAdapter()
        viewModel.movieCastData.observe(this, {
            rv_detail_caster.adapter = casterAdapter
            casterAdapter.setList(DetailParsingUtil.casterParsing(it))
        })
        casterAdapter.setOnCasterClickListener {
            val intent = Intent(this, PersonDetailActivity::class.java)
            intent.putExtra("person_id", it)
            startActivity(intent)
        }
    }

    private fun observeSimilarMovieData(){
        val similarMovieAdapter = OtherMovieAdapter(OtherMovieCase.SIMILAR)
        viewModel.similarMovieData.observe(this, {
            rv_detail_similar_movie.adapter = similarMovieAdapter
            similarMovieAdapter.setList(it)
        })
        similarMovieAdapter.setOnSimilarClickListener {
            val intent = Intent(this, DetailActivity::class.java).putExtra("movie_id", it)
            startActivity(intent)
            finish()
        }
    }

    private fun observeRecommendMovieData(){
        val recommendMovieAdapter = OtherMovieAdapter(OtherMovieCase.RECOMMEND)
        viewModel.recommendMovieData.observe(this, {
            rv_detail_recommend_movie.adapter = recommendMovieAdapter
            recommendMovieAdapter.setList(it)
        })
        recommendMovieAdapter.setOnRecommendClickListener {
            val intent = Intent(this, DetailActivity::class.java).putExtra("movie_id", it)
            startActivity(intent)
            finish()
        }
    }

    private fun observeTrailerMovieData(){
        val trailerAdapter = TrailerAdapter()
        viewModel.movieTrailerData.observe(this, {
            rv_detail_trailer.adapter = trailerAdapter
            trailerAdapter.setList(it)
        })
        trailerAdapter.setOnTrailerClickListener {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            /* key 넘겨주기
            val intent = Intent(this, CLASS).putExtra("key", it)
            startActivity(intent)
             */
        }
    }

    /* view setting */
    private fun recyclerViewSetting(){
        val layout = { context: Context -> LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)}

        rv_detail_caster.apply {
            layoutManager = layout(context)
            setHasFixedSize(true)
        }
        rv_detail_similar_movie.apply{
            layoutManager = layout(context)
            setHasFixedSize(true)
        }
        rv_detail_recommend_movie.apply{
            layoutManager =layout(context)
            setHasFixedSize(true)
        }
        rv_detail_trailer.apply {
            layoutManager = layout(context)
            setHasFixedSize(true)
        }
    }

    private fun toolbarLayoutSetting(){
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_layout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar)

    }

    /* toolbar menu setting */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_save -> {
                Intent(this,ImageSelectActivity::class.java)
                    .putExtra("movie_id", intent?.getIntExtra("movie_id", 89501) ?: 89501)
                    .also{
                        startActivity(it)
                    }
                true
            }
            R.id.action_share -> {
                // 공유하기
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        return true
    }



}