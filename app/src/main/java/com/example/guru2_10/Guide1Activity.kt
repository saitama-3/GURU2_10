package com.example.guru2_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class guide1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide1) // activity_second.xml 연결
    }
}

class guide2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide2) // activity_third.xml 연결
    }
}

class guide3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide3) // activity_fourth.xml 연결
    }
}