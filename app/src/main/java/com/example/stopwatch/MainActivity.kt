package com.example.stopwatch

import android.graphics.Color.blue
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.NonCancellable.start
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var isRunning = false // 실행 여부 확인용 변수
    var timer : Timer? = null //
    var time  = 0

    private lateinit var btn_start: Button
    private lateinit var btn_refresh : Button
    private lateinit var tv_millisecond : TextView
    private lateinit var tv_second : TextView
    private lateinit var tv_minute : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰 가져오기
        btn_start = findViewById(R.id.btn_start)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_millisecond = findViewById(R.id.tv_millisecond)
        tv_second = findViewById(R.id.tv_second)
        tv_minute = findViewById(R.id.tv_minute)

        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_start -> {
                if (isRunning){
                    pause()
                }else{
                    start()
                }
            }
            R.id.btn_refresh -> {
                refresh()
            }
        }
    }

    private fun start(){ // 측정 시작 함수
        btn_start.text = "일시정지"
        btn_start.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        // 스톱워치 시작 로직
        timer = timer(period = 10){
            time++ // 10밀리초 단위 타이머

            // 시간 계산
            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

            runOnUiThread{ // UI 스레드 생성
                if (isRunning){ // UI 업데이트 조건 설정



            // 밀리초
            tv_millisecond.text =
                if (milli_second < 10) ".0${milli_second}" else ".${milli_second}"

            // 초
            tv_second.text = if (second < 10) ":0${second}" else ":${second}"

            //분
            tv_minute.text = "$minute"
             }
        };
    }
}
    private fun pause(){ //일시정지 함수
        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))

        isRunning = false // 멈춤 상태로 전환
        timer?.cancel() // 타이머 멈추기

    }
    private fun refresh(){ // 초기화 함수
        timer?.cancel() // 백그라운드 타이머 멈추기

        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))
        isRunning = false

        //타이머 초기화
        time = 0
        tv_millisecond.text = ".00"
        tv_second.text = ":00"
        tv_minute.text = "00"

    }
}