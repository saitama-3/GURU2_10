package com.example.guru2_10

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_10.CameraActivity

//import com.example.challenge3.databinding.ActivityCameraBinding






class QuizActivity : AppCompatActivity() {
    //DB관련 변수 선언
    private lateinit var dbManager: DBManager
    private lateinit var userId: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        //DBManager 초기화 및 사용자 ID 가져오기
        dbManager = DBManager(this, "userPointsDB",null,1)
        userId =  getUserIdFromSharedPreferences()


        data class Question(
            val id: Int, // 문제 번호
            val question: String, // 문제 텍스트
            val correctAnswer: Int // 정답 (1: "O", 2: "X")
        )

        // 질문 리스트 정의
        val questions = listOf(
            Question(1, "배달음식 포장지나 종이가 이물질이 심해 안 지워진다면 일반쓰레기로 배출할 수 있나요?", 1), // 정답: 예
            Question(2, "종이컵은 분리수거 시 종이류에 버려도 되나요?", 2), // 정답: 아니오
            Question(3, "‘뽁뽁이’로 불리는 에어캡은 비닐로 분리배출해야 한다.", 1), // 정답: 예
            Question(4, "칫솔은 일반 쓰레기로 버려야 한다.", 1), // 정답: 예
            Question(5, "패스트푸드 음료컵의 뚜껑과 빨대는 모두 플라스틱에 포함된다.", 2), // 정답: 아니오
            Question(6, "배달 음식 그릇을 덮은 랩은 비닐류로 배출한다.", 2), // 정답: 아니오
            Question(7, "과일을 감싼 과일망과 포장재는 일반 쓰레기로 배출한다.", 1), // 정답: 예
            Question(8, "고무장갑은 재활용 품목이다.", 2), // 정답: 아니오
            Question(9, "깨진 유리는 모아서 유리류로 분리배출 해야 한다.", 2), // 정답: 아니오
            Question(10, "깨끗하게 씻은 케찹통은 재활용 할 수 있다.", 1) // 정답: 예
        )




        val textQuestion: TextView = findViewById(R.id.text_Question)
        val buttonYes: Button = findViewById(R.id.button_Yes) // O 버튼
        val buttonNo: Button = findViewById(R.id.button_No) // X 버튼
        val buttonAnswer: Button = findViewById(R.id.button_answer)

        // 랜덤으로 질문 하나 선택
        val currentQuestion = questions.random()
        textQuestion.text = currentQuestion.question

        // 버튼 클릭 이벤트
        var isAnswerCorrect = false

        // "O" 버튼 클릭 이벤트
        buttonYes.setOnClickListener {
            isAnswerCorrect = (currentQuestion.correctAnswer == 1)
            Toast.makeText(applicationContext, "O 선택", Toast.LENGTH_LONG).show()
        }

        // "X" 버튼 클릭 이벤트
        buttonNo.setOnClickListener {
            isAnswerCorrect = (currentQuestion.correctAnswer == 2)
            Toast.makeText(applicationContext, "X 선택", Toast.LENGTH_LONG).show()
        }

        // 정답 확인 버튼 클릭 이벤트
        buttonAnswer.setOnClickListener {
            //DB에 사용자 포인트 업데이트
            if (isAnswerCorrect){
                dbManager.updateUserPoints(userId, 15)
            }
            // 정답 여부를 ScoreActivity로 전달
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("IS_ANSWER_CORRECT", isAnswerCorrect)
            startActivity(intent)
        }
    }
    //SharedPreferences에서 사용자 ID 가져오기
    private fun getUserIdFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        return  sharedPreferences.getString("userId", "Guest") ?: "Guest"
    }
}


class ScoreActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        val textResult: TextView = findViewById(R.id.text_Result2)

        // `Intent`에서 정답 여부 가져오기
        val isAnswerCorrect = intent.getBooleanExtra("IS_ANSWER_CORRECT", false)

        // 결과 표시
        textResult.text = if (isAnswerCorrect) "15포인트 획득하셨습니다!" else "오답입니다!"

        val button_home : ImageButton= findViewById(R.id.button_home2)
        button_home.setOnClickListener {
            // MainActivity로 이동하는 Intent 생성 및 시작
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent) // 새 Activity 시작

        }


    }
}

class StartActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val button3: Button = findViewById(R.id.button3)

        button3.setOnClickListener {
            // MainActivity로 이동하는 Intent 생성 및 시작
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent) // 새 Activity 시작

        }

        val button1: Button = findViewById(R.id.button1)

        button1.setOnClickListener {
            // MainActivity로 이동하는 Intent 생성 및 시작
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent) // 새 Activity 시작

        }

    }
}

class Score2Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score2)

        val button_home : ImageButton= findViewById(R.id.button_home2)
        button_home.setOnClickListener {
            // MainActivity로 이동하는 Intent 생성 및 시작
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent) // 새 Activity 시작

        }




    }
}

class RecycleActivity : AppCompatActivity() {
    //DB 관련 변수 선언
    private lateinit var dbManager: DBManager
    private lateinit var userId: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle)

        //DBManager 초기화 및 사용자 ID  가져오기
        dbManager = DBManager(this, "userPointsDB",null,1)
        userId = getUserIdFromSharePreferences()

        val checkBox1: CheckBox = findViewById(R.id.checkBox1)
        val checkBox2: CheckBox = findViewById(R.id.checkBox2)
        val checkBox4: CheckBox = findViewById(R.id.checkBox4)
        val button_next: Button = findViewById(R.id.button_next)



        button_next.setOnClickListener {
            // 모든 체크박스가 체크되었는지 확인
            if (checkBox1.isChecked && checkBox2.isChecked && checkBox4.isChecked) {
                //DB에 사용자 포인트 업데이트
                dbManager.updateUserPoints(userId, 15)
                // ScoreActivity로 이동
                val intent = Intent(this,Score2Activity::class.java)
                startActivity(intent)
            } else {
                // 체크되지 않은 항목이 있을 때 토스트 메시지 표시
                Toast.makeText(this, "모든 체크박스를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //SharedPreferences에서 사용자 ID 가져오기
    private fun getUserIdFromSharePreferences(): String {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("userId", "Guest")?: "Guest"
    }
}






