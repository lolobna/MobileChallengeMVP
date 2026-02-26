plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mobilechallengemvp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mobilechallengemvp"
        minSdk = 24
        targetSdk = 35
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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Core Android
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("androidx.activity:activity:1.8.2")
    implementation(libs.constraintlayout)

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Retrofit + Gson + OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Dagger 2 (Java)
    implementation("com.google.dagger:dagger:2.46.1")
    annotationProcessor("com.google.dagger:dagger-compiler:2.46.1")
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
