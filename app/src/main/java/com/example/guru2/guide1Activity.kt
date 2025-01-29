package com.example.guru2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class guide1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide1) // activity_second.xml 연결
    }
}
