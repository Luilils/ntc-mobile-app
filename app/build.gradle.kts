plugins {
    id("com.android.application")
    // Add the Google Services plugin here
    id("com.google.gms.google-services") // <--- ADD THIS LINE
}

android {
    namespace = "com.ntc.mobileapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ntc.mobileapp"
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
}

dependencies {
    // Import the Firebase BoM (Bill of Materials) to manage Firebase library versions
    // This ensures all Firebase libraries you add are compatible.
    // Replace 'XXXX.X.X' with the latest Firebase Android BoM version.
    // You can find the latest version at: https://firebase.google.com/docs/android/setup#available-libraries
    implementation(platform("com.google.firebase:firebase-bom:32.8.0")) // <--- ADD THIS LINE (use the latest version)

    // Add dependencies for the Firebase products you want to use
    // For example, if you want Authentication and Firestore:
    implementation("com.google.firebase:firebase-auth") // <--- ADD THIS LINE for Authentication
    implementation("com.google.firebase:firebase-firestore") // <--- ADD THIS LINE for Cloud Firestore Database
    implementation("com.google.firebase:firebase-analytics") // <--- ADD THIS LINE for Firebase Analytics

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}