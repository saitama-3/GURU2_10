package com.example.guru2_10

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class JoinActivity : AppCompatActivity() {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var joinButton: Button
    lateinit var edtjId: EditText
    lateinit var edtjPw: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_join)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        joinButton = findViewById(R.id.joinButton)
        edtjId = findViewById(R.id.edtjId)
        edtjPw = findViewById(R.id.edtjPw)

        dbManager = DBManager(this, "userPointsDB", null, 1)

        joinButton.setOnClickListener {
            val str_id = edtjId.text.toString()
            val str_password = edtjPw.text.toString()

            if (str_id.isNotEmpty() && str_password.isNotEmpty()) {
                try {
                    sqlitedb = dbManager.readableDatabase
                    val cursor = sqlitedb.rawQuery("SELECT * FROM user_info WHERE id = '$str_id'", null)
                    if (cursor.count > 0) {
                        Toast.makeText(this, "아이디가 이미 존재합니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        sqlitedb = dbManager.writableDatabase
                        sqlitedb.execSQL("INSERT INTO user_info (id, password, points) VALUES ('$str_id', '$str_password', 0)")
                        sqlitedb.close()

                        Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    cursor.close()
                } catch (e: Exception) {
                    Toast.makeText(this, "회원가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                } finally {
                    sqlitedb.close()
                }
            } else {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}