package com.videosaver.ui.main.splash

import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
//import com.allVideoDownloaderXmaster.OpenForTesting
import com.videosaver.R
import com.videosaver.databinding.ActivitySplashBinding
import com.videosaver.ui.main.base.BaseActivity
import com.videosaver.ui.main.home.MainActivity
import javax.inject.Inject

//@OpenForTesting
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel

    private lateinit var dataBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        dataBinding.viewModel = splashViewModel

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}