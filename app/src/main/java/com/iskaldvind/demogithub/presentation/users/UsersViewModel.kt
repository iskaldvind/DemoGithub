package com.iskaldvind.demogithub.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava2.cachedIn
import com.iskaldvind.demogithub.data.user.model.UserListItem
import com.iskaldvind.demogithub.data.user.repository.IUserPagingRepository
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UsersViewModel(
    private val pagingRepository: IUserPagingRepository
): ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun get(): Flowable<PagingData<UserListItem>> =
        pagingRepository
            .getUsers()
            .map { data -> data.map { UserListItem.fromGitHubUser(it) } }
            .cachedIn(viewModelScope)
}