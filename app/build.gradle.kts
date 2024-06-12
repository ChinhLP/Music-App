plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}


android {
    namespace = "com.example.appmusickotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appmusickotlin"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {


    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.generativeai)
    implementation(libs.androidx.media3.common)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.databinding.runtime)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.media:media:1.6.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation( "androidx.fragment:fragment-ktx:1.7.1")
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.ui.desktop)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("jp.wasabeef:recyclerview-animators:4.0.2")
    implementation ("com.squareup.picasso:picasso:2.71828")

}