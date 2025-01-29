package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class homeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // XML 파일 연결

        // button2와 연결
        val button = findViewById<Button>(R.id.Button2)
        button.setOnClickListener {
            // MainActivity로 이동
            val intent = Intent(this, guidehomeActivity::class.java)
            startActivity(intent)
        }
    }
}

