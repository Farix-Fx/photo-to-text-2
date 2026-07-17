plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.phototext"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.phototext"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.cardview:cardview:1.0.0")

    // ML Kit Text Recognition (on-device, gratis, tanpa API key)
    implementation("com.google.mlkit:text-recognition:16.0.0")

    // Untuk resize/decode bitmap dengan aman
    implementation("androidx.exifinterface:exifinterface:1.3.7")
}
