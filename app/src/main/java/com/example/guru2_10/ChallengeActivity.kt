package com.example.guru2_10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class ChallengeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge) // activity_challenge.xml 연결

        // 텀블러 챌린지 버튼 클릭 시 텀블러 인증 화면으로 이동
        val btnTumbler: Button = findViewById(R.id.btn_tumbler_challenge)
        btnTumbler.setOnClickListener {
            val intent = Intent(this, TumblerChallengeActivity::class.java)
            startActivity(intent)
        }
        
        // 분리수거 챌린지 버튼 클릭 시 이동
        val btnRecycle: Button = findViewById(R.id.btn_recycling_challenge)
        btnRecycle.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        //퀴즈 버튼 클릭 시 이동
        val btnQuiz: Button = findViewById(R.id.btn_quiz)
        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
        
        // 홈으로 가기 버튼 클릭 시 메인 화면(MainActivity)으로 이동
        val btnHome: Button = findViewById(R.id.btn_home)
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // 현재 화면 종료
        }
    }
}
