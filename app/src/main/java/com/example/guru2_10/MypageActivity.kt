package com.example.guru2_10

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

//마이페이지액티비티 클래스 정의
class MypageActivity : AppCompatActivity() {

    //변수 선언
    lateinit var tvName: TextView
    lateinit var tvPoints: TextView
    private var userId: String = "Guest"
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    @SuppressLint("SetTextI18n", "Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        //뷰 바인딩
        tvName = findViewById(R.id.tvName)
        tvPoints = findViewById(R.id.tvPoints)
        val btnChange = findViewById<Button>(R.id.btnChange)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnDel = findViewById<Button>(R.id.btnDel)

        //로그아웃 버튼 클릭 리스너
        btnLogout.setOnClickListener {
            logout()
        }

        //회원 탈퇴 버튼 클릭 리스너
        btnDel.setOnClickListener {
            deleteAccount()
        }
        //DBManager 초기화 및 SharedPreferences에서 사용자 ID 가져오기
        dbManager = DBManager(this, "userPointsDB", null, 1)
        userId = getUserIdFromSharedPreferences()

        //UI 업데이트
        updateUI()

        //포인트 교환 버튼 클릭 리스너
        btnChange.setOnClickListener {
            exchangePoints()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        //액티비티 재개 시 사용자 ID를 다시 가져오고 UI 업데이트
        userId = getUserIdFromSharedPreferences()
        updateUI()
    }

    @SuppressLint("SetTextI18n", "Range")
    private fun updateUI() {
        //사용자 ID가  Guest가 아닌 경우 DB에서 사용자 정보를 가져와서 UI 업데이트
        if (userId != "Guest") {
            try {
                sqlitedb = dbManager.readableDatabase
                val cursor = sqlitedb.rawQuery("SELECT points FROM user_info WHERE id = ?", arrayOf(userId))
                if (cursor.moveToFirst()) {
                    val userPoints = cursor.getInt(cursor.getColumnIndex("points"))
                    tvPoints.text = "포인트 : $userPoints"
                    tvName.text = "아이디 : $userId"
                    Log.d("MypageActivity", "사용자 포인트 $userPoints")
                } else {
                    tvName.text = "아이디 : Guest"
                    tvPoints.text = "포인트 : 0"
                    Log.d("MypageActivity", "사용자 포인트 0")
                }
                cursor.close()
            } catch (e: Exception) {
                Toast.makeText(this, "DB에서 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
            } finally {
                sqlitedb.close()
            }
        } else {
            tvName.text = "아이디 : Guest"
            tvPoints.text = "포인트 : 0"
        }
    }

    //로그아웃 함수
    private fun logout() {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
    //회원 탈퇴 함수
    private fun deleteAccount() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("회원 탈퇴")
        builder.setMessage("회원 탈퇴를 진행하시겠습니까? 모든 데이터가 삭제됩니다.")
        builder.setPositiveButton("예") { dialog, _ ->
            try {
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("DELETE FROM user_info WHERE id = ?", arrayOf(userId))
                sqlitedb.close()
                Toast.makeText(this, "회원 탈퇴 완료", Toast.LENGTH_SHORT).show()
                logout()
            } catch (e: Exception) {
                Toast.makeText(this, "회원 탈퇴 중 오류가 발생", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("아니요") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "회원 탈퇴 취소", Toast.LENGTH_SHORT).show()
        }
        builder.create().show()
    }
    //포인트 교환 함수
    private fun exchangePoints() {
        val userPoints = tvPoints.text.toString().replace("포인트 : ", "").toIntOrNull() ?: 0
        if (userPoints >= 1000) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("포인트 교환")
            builder.setMessage("1000p로 환경 굿즈와 교환하시겠습니까?")
            builder.setPositiveButton("예") { _, _ ->
                val randomNumber = Random.nextInt(0, 100)
                //사용자 포인트 업데이트 및 UI 업데이트
                dbManager.updateUserPoints(userId, -1000)
                updateUI()
                val exchangeBuilder = AlertDialog.Builder(this)
                exchangeBuilder.setTitle("교환 완료")
                exchangeBuilder.setMessage("축하합니다! 교환 번호는 $randomNumber 번 입니다!")
                exchangeBuilder.setPositiveButton("확인") { _, _ ->
                    Toast.makeText(this, "교환 완료", Toast.LENGTH_SHORT).show()
                }
                exchangeBuilder.create().show()
            }
            builder.setNegativeButton("아니요") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "교환 취소", Toast.LENGTH_SHORT).show()
            }
            builder.create().show()
        } else {
            Toast.makeText(this, "포인트가 부족합니다.", Toast.LENGTH_SHORT).show()
        }
    }
    //SharedPreferences에서 userId를 가져오는 함수
    private fun getUserIdFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("userId", "Guest") ?: "Guest"
    }
}
