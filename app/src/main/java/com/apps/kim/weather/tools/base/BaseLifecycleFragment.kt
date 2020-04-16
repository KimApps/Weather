package com.apps.kim.weather.tools.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apps.kim.weather.app.LoginActivity
import com.apps.kim.weather.tools.utils.PrefProvider
import com.apps.kim.weather.tools.utils.bindInterfaceOrThrow

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
abstract class BaseLifecycleFragment<T : BaseVM> : Fragment(), BaseView {

    abstract val viewModelClass: Class<T>

    protected val vm: T by lazy {
        createViewModelFactory()?.let {
            ViewModelProvider(this, it).get(viewModelClass)
        } ?: ViewModelProvider(this).get(viewModelClass)
    }

    protected abstract val layoutId: Int

    abstract fun observeLiveData()

    private var baseView: BaseView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseView = bindInterfaceOrThrow<BaseView>(parentFragment, context)
    }

    override fun onDetach() {
        baseView = null
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeAllLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false)
        return view
    }

    private fun observeAllLiveData() {
        observeLiveData()
        with(vm) {
            isLoadingLD.observe(this@BaseLifecycleFragment, Observer {
                it?.let { showProgress(it) }
            })
        }
    }

    override fun showProgress() {
        baseView?.showProgress()
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) showProgress() else hideProgress()
    }

    override fun hideProgress() {
        baseView?.hideProgress()
    }

    override fun onError(error: Any) {
        baseView?.onError(error)
    }

    override fun showSnackbar(message: String) {
        baseView?.showSnackbar(message)
    }

    override fun createViewModelFactory(): ViewModelProvider.NewInstanceFactory? {
        return null
    }

    override fun showSnackbar(res: Int) {
        baseView?.showSnackbar(res)
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
        // AlertDialog.Builder(context).
    }

    override fun hideSnackBar() {
        baseView?.hideSnackBar()
    }

    override fun showSnackBar(res: Int, actionRes: Int, callback: () -> Unit) {
        baseView?.showSnackBar(res, actionRes, callback)
    }

    override fun isNeedProgress() = true

    fun startLogin() {
        PrefProvider.clearSession()
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }
}