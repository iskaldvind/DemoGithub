package com.iskaldvind.demogithub.data.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class DefaultSchedulers: ISchedulers {
    override fun background(): Scheduler = io.reactivex.schedulers.Schedulers.newThread()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = io.reactivex.schedulers.Schedulers.io()
}