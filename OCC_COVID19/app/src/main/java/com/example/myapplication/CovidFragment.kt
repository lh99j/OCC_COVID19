package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.myapplication.databinding.FragmentCovidBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import javax.xml.parsers.DocumentBuilderFactory


class CovidFragment : Fragment() {

    lateinit var binding : FragmentCovidBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCovidBinding.inflate(inflater, container, false)

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
        //API 정보를 가지고 있는 주소
        var url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey="+key+pageNo+numOfRows+startCreateDt+endCreateDt

        val thread = Thread(NetworkThread_covid(url))
        thread.start() //쓰레드 시작
        thread.join()



        val dec = DecimalFormat("#,###")

        binding.jejuTotal.text = dec.format(jeju_total_string.toInt())
        binding.jejuBoardTotal.text = dec.format(jeju_board_string.toInt())
        binding.jejuDeadTotal.text = dec.format(jeju_dead_string.toInt())
        binding.seoulTotal.text = dec.format(seoul_total_string.toInt())
        binding.seoulBoardTotal.text = dec.format(seoul_board_string.toInt())
        binding.seoulDeadTotal.text = dec.format(seoul_dead_string.toInt())
        binding.totalCovid.text = dec.format(total_string.toInt())
        binding.dead.text = dec.format(death_string.toInt())
        binding.criticallyIll.text = dec.format(board_string.toInt())
        binding.cure.text = dec.format(covidRate.toInt())

        return binding.root
    }
}
