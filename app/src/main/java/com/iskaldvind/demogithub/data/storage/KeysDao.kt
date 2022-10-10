package com.iskaldvind.demogithub.data.storage

import androidx.room.*
import com.iskaldvind.demogithub.data.user.model.Keys

@Dao
interface KeysDao {

    @Query("SELECT * FROM keys")
    fun fetchKeys(): List<Keys>

    @Query("SELECT * FROM keys WHERE id LIKE :id LIMIT 1")
    fun fetchKeysById(id: Int): Keys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveKeys(keys: List<Keys>)

    @Query("DELETE FROM keys")
    fun clear()
}