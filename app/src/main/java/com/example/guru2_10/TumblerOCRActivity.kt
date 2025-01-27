package com.example.guru2_10

import android.graphics.Bitmap
import android.content.Intent
import android.provider.MediaStore
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.FileOutputStream

class TumblerOCRActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val TAG = "TumblerOCR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tumbler_challenge)

        val btnCertify: Button = findViewById(R.id.btn_certify)

        // 인증하기 버튼 클릭 시 카메라 호출
        btnCertify.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // 촬영된 이미지 받아오기
            val imageBitmap = data?.extras?.get("data") as Bitmap
            if (imageBitmap != null) {
                val imageFile = saveImageToFile(imageBitmap)
                if (imageFile != null) {
                    callCloverOCR(imageFile)
                } else {
                    Toast.makeText(this, "이미지 저장 실패", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "이미지를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImageToFile(bitmap: Bitmap): File {
        // 이미지 파일로 저장하는 코드 (파일 경로와 이름 설정)
        val file = File(filesDir, "captured_receipt.jpg")
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
        } catch (e: IOException) {
            Log.e("OCR_ERROR", "이미지 저장 오류", e)
        }
        return file
    }

    private fun callCloverOCR(imageFile: File) {
        // 네이버 클로바 OCR API 키 설정
        val apiUrl = "https://naveropenapi.apigw.ntruss.com/vision/v1/ocr"
        val apiKey = "iuvhKRwEjXej2qZkXg5ecxVoExkXWABiWJGhPmZ1"         // 네이버 클로바 OCR API 키
        val apiSecret = "Ux1D4CcblFhMrizU1LoCe5uV1sVsTpQRNwBbcZfj"  // 네이버 클로바 OCR 시크릿 키

        val client = OkHttpClient()

        // 멀티파트 폼 데이터로 이미지 전송
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                imageFile.name,
                RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageFile)
            )
            .build()

        val request = Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .addHeader("X-NCP-APIGW-API-KEY-ID", apiKey)
            .addHeader("X-NCP-APIGW-API-KEY", apiSecret)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@TumblerOCRActivity,
                        "OCR 요청 실패: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Log.e("OCR_ERROR", "OCR 요청 실패", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(
                            this@TumblerOCRActivity,
                            "OCR 요청 실패: ${response.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return
                }

                val responseBody = response.body?.string()
                if (responseBody != null) {
                    processOCRResult(responseBody)
                }
            }
        })
    }

    private fun processOCRResult(jsonResponse: String) {
        try {
            // JSON 파싱
            val jsonObject = JSONObject(jsonResponse)
            val imagesArray = jsonObject.getJSONArray("images")
            val fieldsArray = imagesArray.getJSONObject(0).getJSONArray("fields")

            // OCR 결과 텍스트 추출
            val extractedText = StringBuilder()
            for (i in 0 until fieldsArray.length()) {
                val text = fieldsArray.getJSONObject(i).getString("inferText")
                extractedText.append(text).append(" ")
            }

            // '텀블러' 텍스트 포함 여부 확인
            runOnUiThread {
                val isSuccess = extractedText.contains("텀블러")
                val intent = Intent(this, CertificationCompleteActivity::class.java)
                intent.putExtra("isSuccess", isSuccess)  // 인증 결과 전달
                startActivity(intent)
                finish()  // 현재 화면 종료 (인증 완료 화면으로 이동)
            }
        } catch (e: Exception) {
            runOnUiThread {
                Toast.makeText(this, "OCR 결과 처리 중 오류 발생", Toast.LENGTH_LONG).show()
            }
            Log.e("OCR_ERROR", "OCR 결과 처리 중 오류 발생", e)
        }
    }
}
