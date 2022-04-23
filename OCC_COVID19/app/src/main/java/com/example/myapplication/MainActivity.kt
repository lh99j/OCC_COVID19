package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btn1.setOnClickListener{
            Log.d("lh99j", "btn1")
        }

        binding.btn2.setOnClickListener{
            Log.d("lh99j", "btn2")
        }

        binding.btn3.setOnClickListener{
            Log.d("lh99j", "btn3")
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}