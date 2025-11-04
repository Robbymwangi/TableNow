plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.googleGmsServices) // Use the alias from the TOML file
}

android {
    namespace = "com.example.tablenow"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tablenow"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation("io.coil-kt:coil:2.7.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Firebase Bill of Materials (BOM)
    // This manages versions for all Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // Cloud Firestore (to store booking data)
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Firebase Authentication (to know *who* is logged in)
    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase Storage (if you host your images in Firebase)
    implementation("com.google.firebase:firebase-storage-ktx")
}