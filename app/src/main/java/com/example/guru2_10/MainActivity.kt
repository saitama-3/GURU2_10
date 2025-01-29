package com.example.guru2_10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 환경 챌린지 버튼 클릭 시 챌린지 화면으로 이동
        val btnChallenge: Button = findViewById(R.id.btn_challenge)
        btnChallenge.setOnClickListener {
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent)
        }
    }
}

//다른 버튼 이동 코드 필요함
