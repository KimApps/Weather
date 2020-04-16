package com.apps.kim.weather.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.apps.kim.weather.R
import com.apps.kim.weather.tools.base.BaseLifecycleActivity

class LoginActivity : BaseLifecycleActivity<MainVM>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }

    override val layoutId = R.layout.activity_login
    override val containerId = R.id.mContainer
    override val viewModelClass = MainVM::class.java
    override fun observeLiveData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
