package com.iskaldvind.demogithub.data.user.repository

import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Observable
import io.reactivex.Single

interface IUserRepository {
    fun getUsers(since: Int, limit: Int): Single<List<User>>
    fun getUserByLogin(login: String): Observable<User>
}