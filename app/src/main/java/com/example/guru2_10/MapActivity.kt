package com.example.guru2_10

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val tumblerIcon1: ImageView = findViewById(R.id.tumblerIcon1)
        val tumblerIcon2: ImageView = findViewById(R.id.tumblerIcon2)

        // 첫 번째 위치 클릭 이벤트
        tumblerIcon1.setOnClickListener {
            Toast.makeText(this, "8번 학생누리관 1층에 텀블러 세척기가 있습니다!", Toast.LENGTH_SHORT).show()
        }

        // 두 번째 위치 클릭 이벤트
        tumblerIcon2.setOnClickListener {
            Toast.makeText(this, "5번 인문사회관 1층에 텀블러 세척기가 있습니다!", Toast.LENGTH_SHORT).show()
        }
    }
}

