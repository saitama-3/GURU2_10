plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.guru2_10"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.guru2_10"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("org.json:json:20210307") // JSON 데이터 파싱
    implementation("com.squareup.okhttp3:okhttp:4.9.1")  // OkHttp 라이브러리 추가
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.9.1") // URLConnection을 사용할\ 경우 추가
    implementation ("androidx.recyclerview:recyclerview:1.2.1")


    implementation ("com.google.mlkit:text-recognition:16.0.1") // Google ML Kit 텍스트 인식 라이브러리
    implementation ("androidx.camera:camera-core:1.4.1") // CameraX 핵심 라이브러리
    implementation ("androidx.camera:camera-camera2:1.4.1") // Camera2 구현을 위한 CameraX 라이브러리
    implementation ("androidx.camera:camera-lifecycle:1.4.1") // CameraX 라이브러리와 Lifecycle 연동을 위한 라이브러리
    implementation ("androidx.camera:camera-view:1.4.1") // CameraX 뷰 클래스를 위한 라이브러리
    
}
