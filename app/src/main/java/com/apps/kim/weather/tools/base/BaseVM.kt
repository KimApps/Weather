package com.apps.kim.weather.tools.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.apps.kim.weather.tools.utils.ApiException
import com.apps.kim.weather.tools.utils.LogoutUtils
import com.apps.kim.weather.tools.utils.NoNetworkException
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
abstract class BaseVM(application: Application) : AndroidViewModel(application) {

    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    val errorLD = MediatorLiveData<Any>()
    val isLoadingLD = MediatorLiveData<Boolean>()
    var snackTextLD = MutableLiveData<String>()
    val onErrorConsumer = Consumer<Throwable> { error ->
        //ще можно кастить так
        //(it as? Exeptions.MessageException)?.let { error->
        //error.msg
        //}

        when (error) {
            is ApiException -> {
                error.statusCode.takeIf { code -> code == 401 }
                    ?.let { LogoutUtils.doLogout() }
                error.statusCode.takeIf { code -> code == 500 }
                    ?.let { snackTextLD.value = error.message }
            }
        }
        hideLoadingProgress()
        // snackTextLD.value = error.message ?: "ERROR"
    }

    fun setLoadingLiveData(vararg mutableLiveData: MutableLiveData<*>) {
        mutableLiveData.forEach { liveData ->
            isLoadingLD.apply {
                this.removeSource(liveData)
                this.addSource(liveData) { this.value = false }
            }
        }
    }

    override fun onCleared() {
        hideLoadingProgress()
        compositeDisposable?.dispose()
        compositeDisposable?.clear()
        compositeDisposable = null
        super.onCleared()
    }


    fun showLoadingProgress() {
        isLoadingLD.value = true
    }

    fun hideLoadingProgress() {
        isLoadingLD.value = false
    }

    private fun hideOrShowProgress(hideOrShowFlag: Boolean) {
        isLoadingLD.value = hideOrShowFlag
    }

    private fun <T> Single<T>.preSubscribe(isShowProgress: Boolean = true): Single<T> {
        hideOrShowProgress(isShowProgress)
        return compose(ioToMainTransformerSingle()).doOnEvent { _, _ -> hideLoadingProgress() }
    }

    private fun addBackgroundSubscription(subscription: Disposable) {
        compositeDisposable?.add(subscription) ?: let {
            compositeDisposable = CompositeDisposable().apply {
                add(subscription)
            }
        }
    }

    protected fun Disposable.addSubscription() = addBackgroundSubscription(this)

    protected fun <T> Flowable<T>.doAsync(
        successful: Consumer<T>,
        error: Consumer<Throwable> = onErrorConsumer,
        isShowProgress: Boolean = true
    ) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
            .addSubscription()
    }

    protected fun <T> Flowable<T>.doAsync(
        successful: MutableLiveData<T>,
        error: Consumer<Throwable> = onErrorConsumer,
        isShowProgress: Boolean = true
    ) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
            .addSubscription()
    }

    protected fun <T> Single<T>.doAsync(
        successful: Consumer<T>,
        error: Consumer<Throwable> = onErrorConsumer,
        isShowProgress: Boolean = true
    ) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
            .addSubscription()
    }

    protected fun <T> Single<T>.doAsync(
        successful: MutableLiveData<T>,
        error: Consumer<Throwable> = onErrorConsumer,
        isShowProgress: Boolean = true
    ) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
            .addSubscription()
    }

    protected fun <T> Observable<T>.doAsync(
        successful: Consumer<T>,
        error: Consumer<Throwable> = onErrorConsumer,
        isShowProgress: Boolean = true
    ) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
            .addSubscription()
    }

    protected fun <T> Observable<T>.doAsync(
        successful: MutableLiveData<T>,
        error: Consumer<Throwable> = onErrorConsumer,
        isShowProgress: Boolean = true
    ) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
            .addSubscription()
    }

}
