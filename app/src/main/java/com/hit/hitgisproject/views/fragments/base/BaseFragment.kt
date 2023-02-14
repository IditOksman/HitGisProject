package com.hit.hitgisproject.views.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    @LayoutRes
    private val layoutId: Int
): Fragment(layoutId) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onHiddenChanged(hidden: Boolean) {
        if(hidden) {
            onHide()
        } else {
            onShow()
        }
    }

    open fun onHide() {}
    open fun onShow() {}
}