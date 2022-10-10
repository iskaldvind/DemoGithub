package com.iskaldvind.demogithub.data.user.repository

import androidx.paging.PagingData
import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Flowable

interface IUserPagingRepository {
    fun getUsers(): Flowable<PagingData<User>>
}