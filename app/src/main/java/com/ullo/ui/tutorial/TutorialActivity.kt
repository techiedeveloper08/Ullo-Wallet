package com.ullo.ui.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.linderaredux.adapter.CustomPagerAdapter
import com.ullo.BR
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityTutorialBinding
import com.ullo.extensions.gone
import com.ullo.extensions.visible
import com.ullo.ui.landing.LandingActivity
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class TutorialActivity : BaseActivity<ActivityTutorialBinding, TutorialViewModel>(), TutorialNavigator {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, TutorialActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    @set:Inject
    var mCustomPagerAdapter: CustomPagerAdapter? = null

    override val viewModel: TutorialViewModel
        get() = ViewModelProviders.of(this, factory).get(TutorialViewModel::class.java)

    private var mActivityTutorialBinding: ActivityTutorialBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_tutorial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityTutorialBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        viewModel.getSession().setShowTutorial(true)

        mActivityTutorialBinding!!.viewpager.apply {
            adapter = mCustomPagerAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    if (position == (mCustomPagerAdapter!!.count - 1)) {
                        mActivityTutorialBinding!!.btnGetStarted.visible()
                        mActivityTutorialBinding!!.btnNext.gone()
                    } else {
                        mActivityTutorialBinding!!.btnGetStarted.gone()
                        mActivityTutorialBinding!!.btnNext.visible()
                    }
                }

            })
        }

        mActivityTutorialBinding!!.tabLayout.apply {
            setupWithViewPager(mActivityTutorialBinding!!.viewpager, true)
        }
    }

    override fun onNextHandle() {
        with(mActivityTutorialBinding!!.viewpager) {
            currentItem += 1
        }
    }

    override fun onGetStartedHandle() {
        val intent = LandingActivity.newIntent(this)
        startActivity(intent)
        finish()
    }
}