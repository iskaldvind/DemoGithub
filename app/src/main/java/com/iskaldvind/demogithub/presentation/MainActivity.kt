package com.iskaldvind.demogithub.presentation

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.iskaldvind.demogithub.presentation.core.BaseActivity
import com.iskaldvind.demogithub.presentation.users.UsersScreen
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class MainActivity: BaseActivity() {

    private val navigator = AppNavigator(this, android.R.id.content)
    val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()
    val activityDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: router.newRootScreen(UsersScreen)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onDestroy() {
        activityDisposable.dispose()
        super.onDestroy()
    }
}