package com.krishworld.hiltexample

import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.krishworld.hiltexample.base.BaseActivity
import com.krishworld.hiltexample.databinding.ActivityMainNavBinding
import com.krishworld.hiltexample.utils.Extensions.gone
import com.krishworld.hiltexample.utils.Extensions.hideWithoutAnimation
import com.krishworld.hiltexample.utils.Extensions.showWithAnimation
import com.krishworld.hiltexample.utils.Extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainNavActivity : BaseActivity() {

    private lateinit var binding: ActivityMainNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //For enable full screen mode
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        binding = ActivityMainNavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationView()
        binding.contentMainNav.burger.setOnClickListener {
            toggleDrawer()
        }
    }

    private fun setupBottomNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.contentDrawerMainNav.navigationView.setupWithNavController(navHostFragment.navController)
        binding.contentMainNav.bottomNavigation.setupWithNavController(navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.SplashFragment) {
                //For disable full screen mode
                binding.contentMainNav.burger.visible()
                window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                if (shouldShowBottomBar(destination.id)) {
                    binding.contentMainNav.bottomNavigation.showWithAnimation(binding.contentMainNav.fragmentContainerView)
                } else {
                    binding.contentMainNav.bottomNavigation.hideWithoutAnimation(binding.contentMainNav.fragmentContainerView)
                }
            } else {
                binding.contentMainNav.burger.gone()
                binding.contentMainNav.bottomNavigation.hideWithoutAnimation(binding.contentMainNav.fragmentContainerView)
            }
        }
    }

    private fun shouldShowBottomBar(selectedItemId: Int): Boolean {
        return selectedItemId in arrayOf(
            R.id.VideoFragment,
            R.id.MainFragment,
            R.id.NetworkApiFragment,
            R.id.LocalizationFragment,
            R.id.DialogTestFragment
        )
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}
