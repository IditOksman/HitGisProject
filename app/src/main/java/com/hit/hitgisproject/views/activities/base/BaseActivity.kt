package com.hit.hitgisproject.views.activities.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.hit.hitgisproject.views.fragments.base.BaseFragment

abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun launchFragment(
        fragment: BaseFragment,
        @IdRes containerViewId: Int,
        tag: String?) {
        supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment, tag)
            .commitAllowingStateLoss()
    }
}