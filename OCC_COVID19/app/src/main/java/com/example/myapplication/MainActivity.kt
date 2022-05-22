package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.databinding.ActivityMainBinding
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.text.SimpleDateFormat
import javax.xml.parsers.DocumentBuilderFactory
import java.time.LocalDate
import java.time.LocalDateTime


var text = ""
var jeju_total_string = ""
var jeju_board_string = ""
var jeju_dead_string = ""
var seoul_total_string = ""
var seoul_board_string = ""
var seoul_dead_string = ""

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


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
        //API 정보를 가지고 있는 주소
        var url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey="+key+pageNo+numOfRows+startCreateDt+endCreateDt
        // 버튼을 누르면 쓰레드 동작
            //쓰레드 생성
        binding.btn.setOnClickListener{
            val thread = Thread(NetworkThread(url))
            thread.start() //쓰레드 시작
            thread.join()
            binding.txt.text = text
        }
    }
}

class NetworkThread(
var url: String): Runnable {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun run() {

        try {

            val xml : Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)


            xml.documentElement.normalize()

            //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
            val list:NodeList = xml.getElementsByTagName("item")

            //list.length-1 만큼 얻고자 하는 태그의 정보를 가져온다
            for(i in 0..list.length-1){

                val n:Node = list.item(i)

                if(n.getNodeType() == Node.ELEMENT_NODE){

                    val elem = n as Element

                    val map = mutableMapOf<String,String>()


                    // 이부분은 어디에 쓰이는지 잘 모르겠다.
                    for(j in 0..elem.attributes.length - 1) {

                        map.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)

                    }


                    if((elem.getElementsByTagName("gubun").item(0).textContent).equals("제주")){
                        jeju_total_string = "${elem.getElementsByTagName("defCnt").item(0).textContent}"
                        jeju_board_string = "${elem.getElementsByTagName("overFlowCnt").item(0).textContent}"
                        jeju_dead_string = "${elem.getElementsByTagName("deathCnt").item(0).textContent}"
                    }else if((elem.getElementsByTagName("gubun").item(0).textContent).equals("서울")){
                        seoul_total_string = "${elem.getElementsByTagName("defCnt").item(0).textContent}"
                        seoul_board_string = "${elem.getElementsByTagName("overFlowCnt").item(0).textContent}"
                        seoul_dead_string = "${elem.getElementsByTagName("deathCnt").item(0).textContent}"
                    }


                }
            }
        } catch (e: Exception) {
            Log.d("TTT", "오픈API"+e.toString())
        }
    }
}