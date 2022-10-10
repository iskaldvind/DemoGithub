package com.iskaldvind.demogithub.data.user.datasource

import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Maybe
import io.reactivex.Single

interface IUserRemoteDataSource {
    fun fetchUsers(since: Int, limit: Int): Single<List<User>>
    fun fetchUserByLogin(login: String): Maybe<User>
}