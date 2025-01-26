package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // XML 파일 연결

        // 첫 번째 ImageButton 클릭 시 화면 전환
        val imageButton = findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // 두 번째 ImageButton 클릭 시 화면 전환
        val imageButton3 = findViewById<ImageButton>(R.id.imageButton2)
        imageButton3.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        // 세 번째 ImageButton 클릭 시 화면 전환
        val imageButton4 = findViewById<ImageButton>(R.id.imageButton3)
        imageButton4.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

        // 네 번째 ImageButton 클릭 시 화면 전환
        val imageButton5 = findViewById<ImageButton>(R.id.imageButton4)
        imageButton5.setOnClickListener {
            val intent = Intent(this, FifthActivity::class.java)
            startActivity(intent)
        }

        // 일반 버튼 클릭 시 동작
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // 원하는 동작 추가
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}
