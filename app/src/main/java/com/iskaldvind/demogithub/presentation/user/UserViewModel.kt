package com.iskaldvind.demogithub.presentation.user

import androidx.lifecycle.ViewModel
import com.iskaldvind.demogithub.data.user.model.User
import com.iskaldvind.demogithub.data.user.repository.IUserRepository
import io.reactivex.Observable

class UserViewModel(
    private val repository: IUserRepository
): ViewModel() {

    fun getUser(login: String): Observable<User> =
        repository
            .getUserByLogin(login = login)
}