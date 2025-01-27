package com.example.guru2_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.view.View
import android.widget.Button
import android.content.Intent
import android.content.SharedPreferences

class CertificationCompleteActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val POINTS_KEY = "total_points" // SharedPreferences 키 값
    private val POINTS_AWARDED = 15 // 지급되는 포인트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification_complete) // 인증완료 화면의 XML 연결

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("TrashChallengePrefs", MODE_PRIVATE)

        // 인증완료 텍스트와 포인트 지급 텍스트를 설정
        val txtCertificationComplete: TextView = findViewById(R.id.txt_certification_status)
        val txtPointsAwarded: TextView = findViewById(R.id.txt_points)
        val btnRetry: Button = findViewById(R.id.btn_retry) // "다시 시도" 버튼

        // 인증 결과 확인
        val isSuccess = intent.getBooleanExtra("isSuccess", false)

        if (isSuccess) {
            // 인증 성공
            txtCertificationComplete.text = "인증완료"
            txtPointsAwarded.text = "$POINTS_AWARDED p를 지급해요!"
            txtPointsAwarded.visibility = View.VISIBLE
            btnRetry.visibility = View.GONE

            // 포인트 지급 처리 (마이페이지에서 확인 가능)
            updatePoints(POINTS_AWARDED)
        } else {
            // 인증 실패
            txtCertificationComplete.text = "인증 실패"
            txtPointsAwarded.visibility = View.GONE
            btnRetry.visibility = View.VISIBLE

            // "다시 시도" 버튼 클릭 이벤트
            btnRetry.setOnClickListener {
                // 인증을 다시 시도하려면 이전 화면으로 돌아가기
                val intent = Intent(this, TumblerOCRActivity::class.java)
                startActivity(intent)
                finish() // 이전 화면으로 돌아가기
            }
        }
    }

    // 포인트 업데이트 함수
    private fun updatePoints(points: Int) {
        val editor = sharedPreferences.edit()
        val currentPoints = sharedPreferences.getInt(POINTS_KEY, 0)
        editor.putInt(POINTS_KEY, currentPoints + points)
        editor.apply()
    }
}
