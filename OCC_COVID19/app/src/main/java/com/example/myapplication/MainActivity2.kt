package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMain2Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding : ActivityMain2Binding
    val fl : FrameLayout by lazy {
        findViewById(R.id.container)
    }
    val bn : BottomNavigationView by lazy {
        findViewById(R.id.bottom_menu)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMain2Binding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportFragmentManager.beginTransaction().add(fl.id, OneFragment()).commit()

        bn.setOnItemSelectedListener {
            replaceFragment(
                when(it.itemId){
                    R.id.menu_home -> OneFragment()
                    R.id.menu_favorites -> TwoFragment()
                    else -> ThreeFragment()
                }
            )
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }
}