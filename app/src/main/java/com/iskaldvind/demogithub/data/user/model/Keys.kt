package com.iskaldvind.demogithub.data.user.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class Keys(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "prev")
    val prev: Int?,
    @ColumnInfo(name = "next")
    val next: Int?
)