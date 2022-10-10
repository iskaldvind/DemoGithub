package com.iskaldvind.demogithub.data.user.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.iskaldvind.demogithub.data.storage.UserDao
import com.iskaldvind.demogithub.data.storage.PagesCache
import com.iskaldvind.demogithub.data.storage.KeysDao
import com.iskaldvind.demogithub.data.user.model.User
import com.iskaldvind.demogithub.data.user.model.Keys
import com.iskaldvind.demogithub.data.schedulers.ISchedulers
import io.reactivex.Single
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class UserPagingMediator(
    private val userDao: UserDao,
    private val keysDao: KeysDao,
    private val repository: IUserRepository,
    private val schedulers: ISchedulers,
    private val pages: PagesCache
): RxRemoteMediator<Int, User>() {

    companion object {
        const val INVALID_PAGE = -1
    }

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, User>
    ): Single<MediatorResult> =
        Single.just(loadType)
            .subscribeOn(schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.prev ?: 0
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        remoteKeys.prev ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        remoteKeys.next ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    repository
                        .getUsers(since = page, limit = pages.itemsLimit)
                        .map { saveToDB(page, loadType, it) }
                        .map<MediatorResult> {
                            MediatorResult
                                .Success(endOfPaginationReached = it.size < pages.itemsLimit)
                        }
                        .onErrorReturn {
                            MediatorResult.Error(it)
                        }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }

    private fun saveToDB(page: Int, loadType: LoadType, data: List<User>): List<User> {
        val ids = data.map { it.id }
        pages.addLast(ids)
        val prevKey = pages.getPrev(page, ids)
        val nextKey = pages.getNext(ids)
        val keys = ids.map { id ->
            Keys(id = id, prev = prevKey, next = nextKey)
        }
        userDao.saveUsers(data)
        keysDao.saveKeys(keys)
        return data
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, User>): Keys? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keysDao.fetchKeysById(id = id)
            }
        }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, User>): Keys {
        val keys = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { user ->
           keysDao.fetchKeysById(user.id)
        }
        if (keys != null) return keys
        val last = userDao.getIds().last()
        return keysDao.fetchKeysById(last)
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, User>): Keys? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { user ->
            keysDao.fetchKeysById(user.id)
        }
}

