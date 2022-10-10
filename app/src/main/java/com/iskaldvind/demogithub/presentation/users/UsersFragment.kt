package com.iskaldvind.demogithub.presentation.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.iskaldvind.demogithub.presentation.core.BaseFragment
import com.iskaldvind.demogithub.R.layout.fragment_users
import com.iskaldvind.demogithub.databinding.FragmentUsersBinding
import com.iskaldvind.demogithub.presentation.MainActivity
import com.iskaldvind.demogithub.presentation.user.UserScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment: BaseFragment(fragment_users) {

    companion object {
        fun newInstance(): Fragment = UsersFragment()
    }

    private val binding: FragmentUsersBinding by viewBinding()
    private val viewModel: UsersViewModel by viewModel()
    private val adapter by lazy {
        UsersAdapter(context = requireContext()) { login -> onItemClick(login = login) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        viewModel
            .get()
            .subscribe { adapter.submitData(lifecycle, it) }
            .also { (requireActivity() as MainActivity).activityDisposable.add(it) }
        binding.containerRefresh.setOnRefreshListener {
            binding.containerRefresh.isRefreshing = false
            adapter.refresh()
        }
    }

    private fun onItemClick(login: String) {
        (requireActivity() as MainActivity).router.navigateTo(UserScreen(login = login))
    }
}