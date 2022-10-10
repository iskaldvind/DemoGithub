package com.iskaldvind.demogithub.presentation.user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.iskaldvind.demogithub.R
import com.iskaldvind.demogithub.presentation.core.BaseFragment
import com.iskaldvind.demogithub.R.layout.fragment_user
import com.iskaldvind.demogithub.arguments
import com.iskaldvind.demogithub.data.user.model.User
import com.iskaldvind.demogithub.databinding.FragmentUserBinding
import com.iskaldvind.demogithub.presentation.MainActivity
import com.iskaldvind.demogithub.presentation.users.UsersScreen
import com.iskaldvind.demogithub.showErrorSnack
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment: BaseFragment(fragment_user) {

    companion object {
        private const val LOGIN: String = "UserFragment.login"
        fun newInstance(login: String): Fragment = UserFragment().arguments(LOGIN to login)
    }

    private val binding: FragmentUserBinding by viewBinding()
    private val viewModel: UserViewModel by viewModel()
    private val login: String by lazy { arguments?.getString(LOGIN).orEmpty() }
    private val activity: MainActivity by lazy { requireActivity() as MainActivity }
    private val colorAccent: Int by lazy { resources.getColor(R.color.accent, null) }
    private val disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI()
        viewModel
            .getUser(login = login)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe (
                { data -> drwUser(data) },
                { error -> showErrorSnack(
                    view = binding.root,
                    message = error.message ?: getString(R.string.data_load_error),
                    color = colorAccent
                )}
            )
            .also { disposable.add(it) }
    }

    private fun configureUI() {
        with(binding) {
            back.setOnClickListener { activity.router.backTo(UsersScreen) }
        }
    }

    private fun drwUser(user: User) {
        with(binding) {
            setPicture(avatar, user.avatar)
            name.text = user.name ?: user.login
            fillField(emailLabel, email, user.email)
            fillField(companyLabel, company, user.company)
            fillField(followersLabel, followers, user.followers)
            fillField(followingLabel, following, user.following)
            fillField(joinedLabel, joined, extractDate(user.joined))
        }
    }

    private fun setPicture(view: ImageView, value: String) {
        Glide.with(requireContext())
            .load(value)
            .circleCrop()
            .into(view)
    }

    private fun fillField(label: TextView, field: TextView, value: String?) {
        if (value.isNullOrEmpty()) return
        setText(label = label, field = field, text = value)
    }

    private fun fillField(label: TextView, field: TextView, value: Int?) {
        if (value == null) return
        setText(label = label, field = field, text = value.toString())
    }

    private fun setText(label: TextView, field: TextView, text: String) {
        listOf(label, field).forEach { it.visibility = View.VISIBLE }
        field.text = text
    }

    private fun extractDate(date: String?): String? =
        if (date.isNullOrEmpty() || !date.contains("T"))
            date else date.split("T").first()

    override fun onStop() {
        disposable.dispose()
        super.onStop()
    }
}