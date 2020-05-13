package com.ullo.ui.splash

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.ViewModelProviders
import com.ullo.BR
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivitySplashBinding
import com.ullo.ui.landing.LandingActivity
import com.ullo.ui.main.MainActivity
import com.ullo.ui.tutorial.TutorialActivity
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: SplashViewModel
        get() = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)

    private var mActivitySplashBinding: ActivitySplashBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivitySplashBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        viewModel.onTimeHandler()

        runOnUiThread {
            val animation = ObjectAnimator.ofInt(mActivitySplashBinding!!.progress, "progress", 100)
            animation.duration = 3000
            animation.interpolator = DecelerateInterpolator()
            animation.start()
        }
    }

    override fun onLandingScreen() {
        LandingActivity.newIntent(this).apply {
            startActivity(this)
        }
        finish()
    }

    override fun onMainScreen() {
        MainActivity.newIntent(this).apply {
            startActivity(this)
        }
        finish()
    }

    override fun onTutorialScreen() {
        TutorialActivity.newIntent(this).apply {
            startActivity(this)
        }
        finish()
    }
}