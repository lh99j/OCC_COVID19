package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.databinding.ActivityMainBinding


var text = ""
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.txt.text = ""

        //키값
        val key = "app key"
        //현재 페이지번호
        val pageNo = "&pageNo=1"
        //한 페이지 결과 수
        val numOfRows = "&numOfRows=10"
        // 검색할 생성일 범위의 시작
        val startCreateDt = "&startCreateDt=20220516"
        //검색할 생성일 범의의 끝
        val endCreateDt = "&endCreateDt=20220516"

        // 버튼을 누르면 쓰레드 동작
        binding.btn.setOnClickListener{
            //쓰레드 생성
            val thread = Thread(NetworkThread(url))
            thread.start() //쓰레드 시작
            thread.join() //멀티 작업 안되게 하려면 start 후 join 입력

            binding.txt.text = text
        }



    }
}