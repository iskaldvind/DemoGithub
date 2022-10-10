package com.iskaldvind.demogithub.presentation.users

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.iskaldvind.demogithub.R
import com.iskaldvind.demogithub.data.user.model.UserListItem
import com.iskaldvind.demogithub.databinding.ItemUserBinding

class UsersAdapter(
    private val context: Context,
    private val onItemClick: (String) -> Unit
) : PagingDataAdapter<UserListItem, UsersViewHolder>(UserDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder =
        UsersViewHolder(
            context = context,
            itemView = layoutInflater.inflate(R.layout.item_user, parent, false),
            onItemClick = onItemClick
        )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UsersViewHolder(
    val context: Context,
    itemView: View,
    val onItemClick: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val viewBinding by viewBinding(ItemUserBinding::bind)

    fun bind(user: UserListItem?) {
        with(viewBinding) {
            if (user == null) return
            root.setOnClickListener { onItemClick.invoke(user.login) }
            Glide.with(context)
                .load(user.avatar)
                .circleCrop()
                .into(userItemAvatar)
            userItemLogin.text = user.login
            val subtitle = context.getString(R.string.field_id) + user.id
            userItemId.text = subtitle
        }
    }
}

private object UserDiffItemCallback: DiffUtil.ItemCallback<UserListItem>() {
    override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean =
        oldItem.id == newItem.id
}