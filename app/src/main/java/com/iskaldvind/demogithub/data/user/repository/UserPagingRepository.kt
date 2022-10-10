package com.iskaldvind.demogithub.data.user.repository

import androidx.paging.*
import androidx.paging.rxjava2.flowable
import com.iskaldvind.demogithub.data.storage.Storage
import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Flowable

class UserPagingRepository(
    private val storage: Storage,
    private val mediator: UserPagingMediator
): IUserPagingRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flowable<PagingData<User>> =
        Pager(
            config = PagingConfig(
                pageSize = 100,
                enablePlaceholders = true,
                maxSize = 200,
                prefetchDistance = 50,
                initialLoadSize = 50
            ),
            remoteMediator = mediator,
            pagingSourceFactory = { storage.getGitHubUserDao().getGitHubUsers() }
        )
        .flowable
}