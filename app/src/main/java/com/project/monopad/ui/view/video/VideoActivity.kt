package com.project.monopad.ui.view.video

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.monopad.R
import com.project.monopad.databinding.ActivityVideoBinding
import com.project.monopad.model.network.response.MovieVideoResultResponse
import com.project.monopad.ui.base.BaseActivity

/**
 * 액티비티를 다이어로그처럼 만드는 방법
 * https://mainia.tistory.com/2027
 *
 * 유튜브 API 라이브러리
 * https://github.com/PRNDcompany/YouTubePlayerView
 */
class VideoActivity : AppCompatActivity() {

    val layoutResourceId: Int
        get() = R.layout.activity_video

    lateinit var viewDataBinding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        viewDataBinding.activity = this
        this.setFinishOnTouchOutside(false)
        initStartView()
    }

    fun initStartView() {
        viewDataBinding.videoYoutubePlayerView.play("0xJxgvJO2Xo")
//        if (intent.hasExtra("key")) {
//            val key = intent.getStringExtra("key")
//            if (key != null) {
//                viewDataBinding.videoYoutubePlayerView.play(key)
//            } //key is youtube id
//        }
    }
    fun finishBtnOnClick(){
        finish()
    }
}
