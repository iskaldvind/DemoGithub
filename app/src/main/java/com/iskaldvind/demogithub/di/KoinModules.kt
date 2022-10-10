package com.iskaldvind.demogithub.di

import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.iskaldvind.demogithub.data.api.IGitHubApi
import com.iskaldvind.demogithub.data.storage.Storage
import com.iskaldvind.demogithub.data.storage.PagesCache
import com.iskaldvind.demogithub.data.user.datasource.IUserLocalDataSource
import com.iskaldvind.demogithub.data.user.datasource.UserRemoteDataSource
import com.iskaldvind.demogithub.data.user.datasource.IUserRemoteDataSource
import com.iskaldvind.demogithub.data.user.datasource.UserLocalDataSource
import com.iskaldvind.demogithub.data.user.repository.*
import com.iskaldvind.demogithub.presentation.user.UserViewModel
import com.iskaldvind.demogithub.presentation.users.UsersViewModel
import com.iskaldvind.demogithub.data.schedulers.SchedulersFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single { IGitHubApi.create() }
    single { Room.databaseBuilder(get(), Storage::class.java, "RoomDB").build() }
    single { get<Storage>().getGitHubUserDao() }
    single { get<Storage>().getKeysDao() }
    single { SchedulersFactory.create() }
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().getNavigatorHolder() }
    single { PagesCache() }
}

val user = module {
    single <IUserRemoteDataSource> { UserRemoteDataSource(gitHubApi = get()) }
    single <IUserLocalDataSource> { UserLocalDataSource(storage = get()) }
    single <IUserRepository> { UserRepository(cloud = get(), storage = get()) }
    single { UserPagingMediator(
        userDao = get(),
        keysDao = get(),
        schedulers = get(),
        pages = get(),
        repository = get()
    ) }
    single <IUserPagingRepository> {
        UserPagingRepository(storage = get(), mediator = get())
    }
    viewModel { UserViewModel(repository = get()) }
    viewModel { UsersViewModel(pagingRepository = get()) }
}