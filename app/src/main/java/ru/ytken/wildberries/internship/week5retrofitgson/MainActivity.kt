package ru.ytken.wildberries.internship.week5retrofitgson

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ytken.wildberries.internship.week5retrofitgson.cicerone.Screens

class MainActivity: AppCompatActivity() {
    private val vm: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val navigator = object : AppNavigator(this, R.id.main_container) {
        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment
        ) {
            // animation
            super.setupFragmentTransaction(
                screen,
                fragmentTransaction,
                currentFragment,
                nextFragment
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.MainScreen())))

    }

    override fun onResume() {
        super.onResume()
        vm.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        vm.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        vm.presenter.onBackPressed()
    }
}