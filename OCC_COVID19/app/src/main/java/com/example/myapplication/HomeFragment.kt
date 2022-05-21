package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    val tabTextList = arrayListOf("연령별 현황", "코로나 현황", "코로나 일지")
    class MyFragmentPagerAdapter(activity:HomeFragment) : FragmentStateAdapter(activity){
        val fragments : List<Fragment>
        init{
            fragments = listOf(AgeFragment(), CovidFragment(), DiaryFragment())
        }
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        val adapter = HomeFragment.MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter


        TabLayoutMediator(binding.tabs, binding.viewpager){ tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        return binding.root
    }
}

