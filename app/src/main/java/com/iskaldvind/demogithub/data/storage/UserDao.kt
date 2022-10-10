package com.iskaldvind.demogithub.data.storage

import androidx.paging.PagingSource
import androidx.room.*
import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT id FROM users")
    fun getIds(): List<Int>

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getGitHubUsers(): PagingSource<Int, User>

    @Query("SELECT * FROM users WHERE login LIKE :login LIMIT 1")
    fun fetchUserByLogin(login: String): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUsers(users: List<User>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

    @Query("DELETE FROM users")
    fun clear()
}