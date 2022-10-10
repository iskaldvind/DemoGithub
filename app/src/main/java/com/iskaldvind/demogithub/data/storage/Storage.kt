package com.iskaldvind.demogithub.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iskaldvind.demogithub.data.user.model.User
import com.iskaldvind.demogithub.data.user.model.Keys

@Database(exportSchema = false, entities = [User::class, Keys::class], version = 4)
abstract class Storage : RoomDatabase() {
    abstract fun getGitHubUserDao(): UserDao
    abstract fun getKeysDao(): KeysDao
}