package com.ullo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ullo.R
import dagger.android.support.AndroidSupportInjection


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(), BaseNavigator {

    lateinit var mFragmentNavigation: FragmentNavigation
    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            mFragmentNavigation = context as FragmentNavigation
            this.baseActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)
    }

    override fun handleError(error: String) {
        activity?.run {
            AlertDialog.Builder(this)
                    .setTitle("Opps!")
                    .setIcon(R.drawable.error)
                    .setMessage(error)
                    .setPositiveButton(android.R.string.yes) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
        }
    }

    override fun onInternetConnectionError() {
        activity?.run {
            AlertDialog.Builder(this)
                    .setTitle("Opps!")
                    .setIcon(R.drawable.error)
                    .setMessage(getString(R.string.please_check_your_internet_connection_or_try_again_later))
                    .setPositiveButton(android.R.string.yes) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
        }
    }
}