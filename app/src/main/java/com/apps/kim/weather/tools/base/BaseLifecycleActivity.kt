package com.apps.kim.weather.tools.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apps.kim.weather.R
import com.apps.kim.weather.tools.extensions.hide
import com.apps.kim.weather.tools.extensions.hideKeyboard
import com.apps.kim.weather.tools.extensions.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.include_progress.*

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
abstract class BaseLifecycleActivity<T : BaseVM> : FragmentActivity(), BaseView {

    protected abstract val viewModelClass: Class<T>

    protected abstract val containerId: Int

    protected abstract val layoutId: Int

    protected val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
            viewModelClass
        )
    }

    private val progressObserver = Observer<Boolean> { it?.let { showProgress(it) } }

    private val errorObserver = Observer<Any> {}

    abstract fun observeLiveData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        observeAllLiveData()
    }

    private fun observeAllLiveData() {
        observeLiveData()
        with(viewModel) {
            isLoadingLD.observe(this@BaseLifecycleActivity, progressObserver)
            errorLD.observe(this@BaseLifecycleActivity, errorObserver)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(containerId)
            ?.onActivityResult(requestCode, resultCode, data)
    }

    protected open fun replaceFR(fragment: Fragment, needToAddToBackStack: Boolean = true) {
        hideKeyboard()
        val name = fragment.javaClass.simpleName
        with(supportFragmentManager.beginTransaction()) {
            setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left
//                R.anim.enter_from_left,
//                R.anim.exit_to_right
            )
            replace(containerId, fragment, name)
            if (needToAddToBackStack) {
                if (supportFragmentManager.findFragmentByTag(name) == null) addToBackStack(name)
            }
            commit()
        }
    }

    protected open fun replaceFastFR(fragment: Fragment, needToAddToBackStack: Boolean = true) {
        hideKeyboard()
        val name = fragment.javaClass.simpleName
        with(supportFragmentManager.beginTransaction()) {
            replace(containerId, fragment, name)
            if (needToAddToBackStack) {
                if (supportFragmentManager.findFragmentByTag(name) == null) addToBackStack(name)
            }
            commit()
        }
    }

    protected open fun addFR(fragment: Fragment, needToAddToBackStack: Boolean = true) {
        hideKeyboard()
        val name = fragment.javaClass.simpleName
        with(supportFragmentManager.beginTransaction()) {
            add(containerId, fragment, name)
            if (needToAddToBackStack) {
                addToBackStack(name)
            }
            commit()
        }
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) showProgress() else hideProgress()
    }

    override fun showProgress() {
        progressView?.show()
    }

    override fun hideProgress() {
        progressView?.hide(false)
    }

    override fun onError(error: Any) {
        hideProgress()
        // showSnackbar(error.toString(), 0)
    }

    override fun showSnackbar(message: String) {
        Snackbar.make(findViewById(containerId), message, Snackbar.LENGTH_LONG).show()
    }

    override fun showSnackbar(@StringRes res: Int) {
        Snackbar.make(findViewById(containerId), getString(res), Snackbar.LENGTH_LONG).show()
    }

    override fun showAlert(
        message: String,
        title: String?,
        cancelable: Boolean,
        positiveRes: Int?,
        positiveFun: () -> Unit,
        negativeRes: Int?,
        negativeFun: () -> Unit
    ) {
        // not implemented yet
    }

    override fun hideSnackBar() {
        // not implemented yet
    }

    override fun showSnackBar(res: Int, actionRes: Int, callback: () -> Unit) {
        // not implemented yet
    }

    override fun createViewModelFactory(): ViewModelProvider.NewInstanceFactory? = null

    override fun isNeedProgress(): Boolean = true
}