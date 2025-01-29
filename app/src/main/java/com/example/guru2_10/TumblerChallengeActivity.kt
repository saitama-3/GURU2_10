package com.example.guru2_10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class TumblerChallengeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tumbler_challenge) // activity_tumbler_challenge.xml 연결

        // 인증하기 버튼 설정
        val btnCertify: Button = findViewById(R.id.btn_certify)

        // 버튼 클릭 이벤트 처리
        btnCertify.setOnClickListener {
            // 인증 화면으로 이동
            val intent = Intent(this, TumblerOCRActivity::class.java)
            startActivity(intent)
        }
    }
}
