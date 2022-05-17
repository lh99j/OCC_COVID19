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
import javax.xml.parsers.DocumentBuilderFactory


var text = ""
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.txt.text = ""

        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        //키값
        val key = "Decoding key"
        //현재 페이지번호
        val pageNo = "&pageNo=1"
        //한 페이지 결과 수
        val numOfRows = "&numOfRows=10"
        // 검색할 생성일 범위의 시작
        val startCreateDt = "&startCreateDt=20220516"
        //검색할 생성일 범의의 끝
        val endCreateDt = "&endCreateDt=20220516"
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


                    println("=========${i+1}=========")
                    text += "${i + 1}번 캠핑장 \n"

                    println("1. 주소 : ${elem.getElementsByTagName("createDt").item(0).textContent}")
                    text += "1. 주소 : ${elem.getElementsByTagName("createDt").item(0).textContent} \n"

                }
            }
        } catch (e: Exception) {
            Log.d("TTT", "오픈API"+e.toString())
        }
    }
}