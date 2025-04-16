import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
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

        val webClientId = properties.getProperty("WEB_CLIENT_ID")
            ?: throw GradleException("WEB_CLIENT_ID not found in keys.properties")


        buildConfigField("String", "TMDB_API", "\"$tmdbApi\"")
        buildConfigField("String", "WEB_CLIENT_ID", "\"$webClientId\"")
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
    implementation(Dependencies.Hilt.ANDROID)
    ksp(Dependencies.Hilt.COMPILER)

    //navigation
    implementation(Dependencies.Hilt.NAVIGATION)
    implementation(Dependencies.Navigation.COMPOSE)

    //coil
    implementation(Dependencies.Coil.compose)
    implementation(Dependencies.Coil.network)


    //Accompanist
    implementation(Dependencies.Accompanist.systemUi)

    //icons
    implementation(Dependencies.Material.EXTENDED_ICONS)

    //firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)


    // modules
    implementation(project(Modules.SHARED_UI))
    implementation(project(Modules.CORE))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.AUTH))
    implementation(project(Modules.TRENDING))
    implementation(project(Modules.SHOWS))
    implementation(project(Modules.MOVIES))
    implementation(project(Modules.SEARCH))
    implementation(project(Modules.SETTINGS))
    implementation(project(Modules.NAVIGATION))
    implementation(project(Modules.PREFS))
}