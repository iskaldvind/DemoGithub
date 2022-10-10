package com.iskaldvind.demogithub.data.user.model

data class UserListItem (
    val id: Int,
    val avatar: String,
    val login: String,
) {

    companion object Mapper {
        fun fromGitHubUser(user: User): UserListItem =
            UserListItem(
                id = user.id,
                avatar = user.avatar,
                login = user.login
            )
    }
}