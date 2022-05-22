package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentAgeBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class AgeFragment : Fragment() {

    lateinit var binding : FragmentAgeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgeBinding.inflate(inflater, container, false)

        //현재시간 구하기
        val Time : Long = System.currentTimeMillis()
        val Timestr = SimpleDateFormat("yyyy-MM-dd").format(Time)
        val CurrentTime = Timestr.replace("[^0-9]".toRegex(), "")

        //키값
        val key = "${BuildConfig.App_key}"
        //현재 페이지번호
        val pageNo = "&pageNo=1"
        //한 페이지 결과 수
        val numOfRows = "&numOfRows=10"
        // 검색할 생성일 범위의 시작
        val startCreateDt = "&startCreateDt=${CurrentTime}"
        //검색할 생성일 범의의 끝
        val endCreateDt = "&endCreateDt=${CurrentTime}"
        //API 정보를 가지고 있는 주소(코로나 연령별 현황)
        var age_url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson?serviceKey="+key+pageNo+numOfRows+startCreateDt+endCreateDt

        //API 정보를 가지고 있는 주소(코로나 전체 현황)
        var covid_url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey="+key+pageNo+numOfRows+startCreateDt+endCreateDt


        val covid_thread = Thread(NetworkThread_covid(covid_url))
        covid_thread.start() //쓰레드 시작
        covid_thread.join()

        val age_thread = Thread(NetworkThread_age(age_url))
        age_thread.start() //쓰레드 시작
        age_thread.join()

        val dec = DecimalFormat("#,###")

        binding.totalCovid.text = dec.format(total_string.toInt())
        binding.dead.text = dec.format(death_string.toInt())
        binding.criticallyIll.text = dec.format(board_string.toInt())
        binding.cure.text = dec.format(covidRate.toInt())

        binding.manTotal.text = dec.format(man_total.toInt())
        binding.manDeathTotal.text = dec.format(man_dead_total.toInt())
        binding.manDeathRate.text = man_dead_Rate
        binding.womanTotal.text = dec.format(woman_total.toInt())
        binding.womanDeathTotal.text = dec.format(woman_dead_total.toInt())
        binding.womanDeathRate.text = woman_dead_Rate

        binding.age0Total.text = dec.format(age0.toInt())
        binding.age10Total.text = dec.format(age10.toInt())
        binding.age20Total.text = dec.format(age20.toInt())
        binding.age30Total.text = dec.format(age30.toInt())
        binding.age40Total.text = dec.format(age40.toInt())
        binding.age50Total.text = dec.format(age50.toInt())
        binding.age60Total.text = dec.format(age60.toInt())
        binding.age70Total.text = dec.format(age70.toInt())
        binding.age80Total.text = dec.format(age80.toInt())


        return binding.root
    }
}