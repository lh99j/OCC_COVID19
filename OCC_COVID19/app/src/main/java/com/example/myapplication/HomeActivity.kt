package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    class MyFragmentPagerAdapter(activity:HomeActivity) : FragmentStateAdapter(activity){
        val fragments : List<Fragment>
        init{
            fragments = listOf(OneFragment(), TwoFragment())
        }
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)


        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter


        TabLayoutMediator(binding.tabs, binding.viewpager){ tab, position ->
            tab.text = "Tab${(position + 1)}"
        }.attach()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}