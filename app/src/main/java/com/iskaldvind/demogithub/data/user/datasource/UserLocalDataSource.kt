package com.iskaldvind.demogithub.data.user.datasource

import com.iskaldvind.demogithub.data.storage.Storage
import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Maybe
import io.reactivex.Single

class UserLocalDataSource(
    private val storage: Storage
): IUserLocalDataSource {

    override fun retain(user: User): Single<User> =
        Single
            .fromCallable { user }
            .doOnSuccess {
                storage.getGitHubUserDao().saveUser(it)
            }

    override fun fetchUserByLogin(login: String): Maybe<User> =
        storage
            .getGitHubUserDao()
            .fetchUserByLogin(login)
            .toMaybe()
}