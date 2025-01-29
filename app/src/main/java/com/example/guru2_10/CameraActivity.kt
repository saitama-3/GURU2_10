package com.example.guru2_10

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.guru2_10.R
import com.example.guru2_10.RecycleActivity

class CameraActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_CAMERA_PERMISSION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera) // activity_camera.xml 레이아웃 설정

        val btnCapture: Button = findViewById(R.id.btnCapture) // btnCapture 버튼 참조
        val button4: Button = findViewById(R.id.button4) // button4 참조

        // 카메라 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }

        // btnCapture 클릭 시 카메라 호출
        btnCapture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        // button4 클릭 시 RecycleActivity로 이동
        button4.setOnClickListener {
            val intent = Intent(this, RecycleActivity::class.java)
            startActivity(intent)
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인되었을 때 카메라 호출
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            } else {
                // 권한이 거부되었을 때
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 촬영된 이미지 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // 촬영된 이미지 받아오기
            val imageBitmap = data?.extras?.get("data") as Bitmap
            if (imageBitmap != null) {
                // 이미지 표시용 ImageView에 Bitmap 설정
                val imageView2: ImageView = findViewById(R.id.imageView2)
                imageView2.setImageBitmap(imageBitmap)
            } else {
                Toast.makeText(this, "이미지를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
