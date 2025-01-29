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
        val tumblerIcon3: ImageView = findViewById(R.id.tumblerIcon3)
        val tumblerIcon4: ImageView = findViewById(R.id.tumblerIcon4)

        // 첫 번째 위치 클릭 이벤트
        tumblerIcon1.setOnClickListener {
            Toast.makeText(this, "8번 학생누리관 1층에 텀블러 세척기가 있습니다!", Toast.LENGTH_SHORT).show()
        }

        // 두 번째 위치 클릭 이벤트
        tumblerIcon2.setOnClickListener {
            Toast.makeText(this, "5번 인문사회관 1층에 텀블러 세척기가 있습니다!", Toast.LENGTH_SHORT).show()
        }
        // 세 번째 위치 클릭 이벤트
        tumblerIcon3.setOnClickListener {
            Toast.makeText(this, "7번 중앙도서관 1층 정수기 옆에 텀블러 세척기가 있습니다!", Toast.LENGTH_SHORT).show()
        }
        // 네 번째 위치 클릭 이벤트
        tumblerIcon4.setOnClickListener {
            Toast.makeText(this, "2번 50주년 기념관 4층, 5층 정수기 옆에 텀블러 세척기가 있습니다!", Toast.LENGTH_SHORT).show()
        }
    }
}

