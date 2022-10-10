package com.iskaldvind.demogithub.data.schedulers

import io.reactivex.Scheduler

interface ISchedulers {
    fun background(): Scheduler
    fun main(): Scheduler
    fun io(): Scheduler
}