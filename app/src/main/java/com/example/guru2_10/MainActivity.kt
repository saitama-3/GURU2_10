package com.example.guru2_10


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // XML 연결

        // 버튼 연결
        val mapButton: Button = this.findViewById(R.id.hButton3)
        val guideButton: Button = this.findViewById(R.id.hButton2)
        val myPageButton: Button = this.findViewById(R.id.hButton1)
        val challengeButton: Button = this.findViewById(R.id.hButton)

        // 버튼 클릭 이벤트
        mapButton.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        guideButton.setOnClickListener {
            startActivity(Intent(this, GuidehomeActivity::class.java))
        }

        myPageButton.setOnClickListener {
            startActivity(Intent(this, MypageActivity::class.java))
        }

        challengeButton.setOnClickListener {
            startActivity(Intent(this, ChallengeActivity::class.java))
        }
    }
}