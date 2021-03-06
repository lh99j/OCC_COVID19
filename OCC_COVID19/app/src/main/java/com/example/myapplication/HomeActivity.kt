package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    val fl : FrameLayout by lazy {
        findViewById(R.id.container)
    }
    val bn : BottomNavigationView by lazy {
        findViewById(R.id.bottom_menu)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(fl.id, HomeFragment()).commit()


        bn.setOnItemSelectedListener {
            replaceFragment(
                when(it.itemId){
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_favorites -> FavlistFragment()
                    R.id.menu_search -> SearchFragment()
                    else -> SettingFragment()
                }
            )
            true
        }

    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }


}

