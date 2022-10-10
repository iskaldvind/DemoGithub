package com.iskaldvind.demogithub.data.user.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int? = 0,
    @SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int? = 0,
    @SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String? = "",
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = "",
    @SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String? = "",
    @SerializedName("created_at")
    @ColumnInfo(name = "joined")
    val joined: String? = ""
)