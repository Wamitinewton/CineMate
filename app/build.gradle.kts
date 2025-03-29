import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.newton.cinemate"
    compileSdk = 35
    val properties = Properties()


    defaultConfig {

        try {
            val keystoreFile = rootProject.file("keys.properties")
            if (keystoreFile.exists()) {
                properties.load(keystoreFile.inputStream())
            } else {
                throw GradleException("keys.properties file not found")
            }
        } catch (e: Exception) {
            logger.warn("Warning: ${e.message}")
        }

        val tmdbApi = properties.getProperty("TMDB_API")
            ?: throw GradleException("TMDB_API not found in keys.properties")


        buildConfigField("String", "TMDB_API", "\"$tmdbApi\"")
        applicationId = "com.newton.cinemate"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.retrofit2KotlinxConverter)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.Retrofit.okhttpLogger)
    implementation(Dependencies.Kotlinx.serialization)

    //hilt
    implementation(Dependencies.Hilt.android)
    ksp(Dependencies.Hilt.compiler)

    //navigation
    implementation(Dependencies.Hilt.navigation)
    implementation(Dependencies.Navigation.compose)

    //coil
    implementation(Dependencies.Coil.compose)
    implementation(Dependencies.Coil.network)


    //Accompanist
    implementation(Dependencies.Accompanist.systemUi)

    // modules
    implementation(project(Modules.sharedUi))
    implementation(project(Modules.core))
    implementation(project(Modules.network))
    implementation(project(Modules.auth))
    implementation(project(Modules.navigation))
}