package com.example.guru2_10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class LoginActivity : AppCompatActivity() {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var edtId: EditText
    lateinit var edtPw: EditText
    lateinit var btnLogin: Button
    lateinit var btnJoin: Button
    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtId = findViewById(R.id.edtId)
        edtPw = findViewById(R.id.edtPw)
        btnLogin = findViewById(R.id.btnLogin)
        btnJoin = findViewById(R.id.btnJoin)
        sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)

        dbManager = DBManager(this, "userPointsDB", null, 1)

        btnLogin.setOnClickListener {
            val str_id = edtId.text.toString()
            val str_password = edtPw.text.toString()

            if (str_id.isNotEmpty() && str_password.isNotEmpty()) {
                try {
                    sqlitedb = dbManager.readableDatabase
                    val cursor = sqlitedb.rawQuery("SELECT * FROM user_info WHERE id = '$str_id' AND password = '$str_password'", null)

                    if (cursor.moveToFirst()) {
                        with(sharedPreferences.edit()) {
                            putString("userId", str_id)
                            apply()
                        }

                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "아이디 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    cursor.close()
                } catch (e: Exception) {
                    Toast.makeText(this, "로그인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                } finally {
                    sqlitedb.close()
                }
            } else {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btnJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}

