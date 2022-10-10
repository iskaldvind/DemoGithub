package com.iskaldvind.demogithub.data.user.repository

import com.iskaldvind.demogithub.data.user.datasource.IUserLocalDataSource
import com.iskaldvind.demogithub.data.user.datasource.IUserRemoteDataSource
import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Observable
import io.reactivex.Single

class UserRepository(
    private val cloud: IUserRemoteDataSource,
    private val storage: IUserLocalDataSource
) : IUserRepository {

    override fun getUsers(since: Int, limit: Int) : Single<List<User>> =
        cloud
            .fetchUsers(since = since, limit = limit)

    override fun getUserByLogin(login: String): Observable<User> =
        Observable
            .merge(
                storage
                    .fetchUserByLogin(login = login)
                    .toObservable(),
                cloud
                    .fetchUserByLogin(login = login)
                    .toSingle()
                    .flatMap { storage.retain(user = it) }
                    .toObservable()
            )
}