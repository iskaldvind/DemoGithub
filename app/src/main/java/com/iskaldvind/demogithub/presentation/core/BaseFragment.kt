package com.iskaldvind.demogithub.presentation.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.iskaldvind.demogithub.data.schedulers.ISchedulers
import org.koin.android.ext.android.inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    protected val schedulers: ISchedulers by inject()
}