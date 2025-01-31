package com.example.guru2_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.view.View
import android.widget.Button
import android.content.Intent

class CertificationCompleteActivity : AppCompatActivity() {

    //DB관련 변수 선언
    private lateinit var dbManager: DBManager
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification_complete)

        //DBManager 초기화 및 사용자 ID 가져오기
        dbManager = DBManager(this, "userPointsDB",null,1)
        userId =  getUserIdFromSharedPreferences()

        // 인증완료 텍스트와 포인트 지급 텍스트를 설정
        val txtCertificationComplete: TextView = findViewById(R.id.txt_certification_status)
        val txtPointsAwarded: TextView = findViewById(R.id.txt_points)
        val btnGoHome: Button = findViewById(R.id.btn_go_home) // "챌린지 홈으로 가기" 버튼

        // 인증 성공
        txtCertificationComplete.text = "인증완료"
        txtPointsAwarded.text = "15p를 지급해요!"
        txtPointsAwarded.visibility = View.VISIBLE


        //DB에 사용자 포인트 업데이트
        dbManager.updateUserPoints(userId, 15)


            // "챌린지 홈으로 가기" 버튼 클릭 이벤트
            btnGoHome.setOnClickListener {
                val intent = Intent(this, ChallengeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // 이전 액티비티 스택 제거 후 이동
                startActivity(intent)
                finish()
            }
    }

    //SharedPreferences에서 사용자 ID 가져오기
    private fun getUserIdFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        return  sharedPreferences.getString("userId", "Guest") ?: "Guest"
    }
}

