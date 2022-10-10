package com.iskaldvind.demogithub.data.schedulers

object SchedulersFactory {
    fun create(): ISchedulers = DefaultSchedulers()
}