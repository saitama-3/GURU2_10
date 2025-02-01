package com.example.guru2_10

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class GuidehomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guidehome) // XML 파일 연결

        // 첫 번째 ImageButton 클릭 시 화면 전환
        val imageButton = findViewById<ImageButton>(R.id.ghimageButton)
        imageButton.setOnClickListener {
            val intent = Intent(this, Guide1Activity::class.java)
            startActivity(intent)
        }

        // 두 번째 ImageButton 클릭 시 화면 전환
        val imageButton3 = findViewById<ImageButton>(R.id.ghimageButton2)
        imageButton3.setOnClickListener {
            val intent = Intent(this, Guide2Activity::class.java)
            startActivity(intent)
        }

        // 세 번째 ImageButton 클릭 시 화면 전환
        val imageButton4 = findViewById<ImageButton>(R.id.ghimageButton3)
        imageButton4.setOnClickListener {
            val intent = Intent(this, Guide3Activity::class.java)
            startActivity(intent)
        }

        // 네 번째 ImageButton 클릭 시 화면 전환
        val imageButton5 = findViewById<ImageButton>(R.id.ghimageButton4)
        imageButton5.setOnClickListener {
            val intent = Intent(this, Guide4Activity::class.java)
            startActivity(intent)
        }

        // 일반 버튼 클릭 시 동작
        val button = findViewById<Button>(R.id.ghbutton)
        button.setOnClickListener {
            // 원하는 동작 추가
            val intent = Intent(this, Guide5Activity::class.java)
            startActivity(intent)
        }
    }
}

