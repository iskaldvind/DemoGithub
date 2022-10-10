package com.iskaldvind.demogithub.data.user.datasource

import com.iskaldvind.demogithub.data.api.IGitHubApi
import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Maybe
import io.reactivex.Single

class UserRemoteDataSource(
    private val gitHubApi: IGitHubApi
): IUserRemoteDataSource {

    override fun fetchUsers(since: Int, limit: Int): Single<List<User>> =
        gitHubApi
            .fetchUsers(since = since, limit = limit)

    override fun fetchUserByLogin(login: String): Maybe<User> =
        gitHubApi
            .fetchUserByLogin(login = login)
            .toMaybe()
}