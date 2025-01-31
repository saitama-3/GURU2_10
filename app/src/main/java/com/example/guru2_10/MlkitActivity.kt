package com.example.guru2_10

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.Executors

class MlkitActivity : AppCompatActivity() {
    //플래그 변수 추가
    private var isProcessingSuccessful = false
    // UI 구성 요소 정의
    private lateinit var previewView: PreviewView // 카메라 미리보기 화면
    private lateinit var textResult: TextView // 인식된 텍스트 표시용 텍스트뷰

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlkit) // 레이아웃 파일이 올바른지 확인

        // UI 요소 초기화
        previewView = findViewById(R.id.previewView)
        textResult = findViewById(R.id.textResult)

        // 카메라 실행
        startCamera()
    }

    private fun startCamera() {
        // 카메라 프로바이더 인스턴스를 가져옴
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // 카메라 제공자 가져오기
            val cameraProvider = cameraProviderFuture.get()

            // 미리보기 설정
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider // 미리보기 UI에 연결
            }

            // 이미지 분석기 설정 (텍스트 인식 처리용)
            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    // 실시간으로 프레임을 분석하며, processImageProxy() 호출
                    it.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                        processImageProxy(imageProxy) // OCR 분석 실행
                    }
                }

            // 후면 카메라 선택
            val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 기존의 카메라 바인딩 해제 후 다시 바인딩 (중복 방지)
                cameraProvider.unbindAll()

                // 카메라를 앱의 생명 주기와 연결 (Activity가 종료되면 카메라도 자동 종료됨)
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, "카메라 바인딩 실패", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        // 이미지가 존재하고, 처리 성공 여부 플래그가 false인 경우만 실행
        if (mediaImage != null && !isProcessingSuccessful) {
            // ML Kit에서 사용 가능한 InputImage 객체 생성
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            // 텍스트 인식을 위한 ML Kit의 TextRecognition 클라이언트 생성
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)


            // 이미지에서 텍스트 인식 수행
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val detectedText = visionText.text
                    textResult.text = detectedText // 인식된 텍스트 UI에 표시

                    //"Tumbler"가 감지 되었을 때 처리
                    if (detectedText.contains("Tumbler", true) && !isProcessingSuccessful) {
                        Log.i(TAG, "텍스트 인식 성공: Tumbler")
                        textResult.text = "성공: Tumbler 인증"

                        //성공 여부 플래그 설정 (중복 처리 방지)
                        isProcessingSuccessful = true

                        // 인증 완료 화면으로 이동
                        val intent = Intent(this, CertificationCompleteActivity::class.java)
                        startActivity(intent)
                    }

                    // 이미지 리소스 해제
                    imageProxy.close()
                }
                .addOnFailureListener { e ->
                    // 인식 실패 시 로그 출력
                    Log.e(TAG, "텍스트 인식 실패", e)
                    imageProxy.close() // 이미지 리소스 해제
                }
        } else {
            imageProxy.close() // 유효하지 않은 이미지일 경우 바로 해제
        }
    }

    // 로그 태그 정의
    companion object {
        private const val TAG = "MlkitActivity"
    }
}