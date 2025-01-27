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
    }
}
