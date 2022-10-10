package com.iskaldvind.demogithub.data.user.datasource

import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Maybe
import io.reactivex.Single

interface IUserLocalDataSource {
    fun retain(user: User): Single<User>
    fun fetchUserByLogin(login: String): Maybe<User>
}