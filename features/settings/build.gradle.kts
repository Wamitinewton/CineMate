plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.newton.profile"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
//    implementation(libs.material)
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

    //hilt
    implementation(Dependencies.Hilt.ANDROID)
    ksp(Dependencies.Hilt.COMPILER)

    //navigation
    implementation(Dependencies.Hilt.NAVIGATION)
    implementation(Dependencies.Navigation.COMPOSE)

    //coil
    implementation(Dependencies.Coil.compose)
    implementation(Dependencies.Coil.network)

    //extendedIcons
    implementation(Dependencies.Material.EXTENDED_ICONS)

    //timber
    implementation(Dependencies.Logging.TIMBER)

    //paging
    implementation(Dependencies.Paging.compose)
    implementation(Dependencies.Paging.runtime)


    //modules
    implementation(project(Modules.CORE))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.NAVIGATION))
    implementation(project(Modules.SHARED_UI))
    implementation(project(Modules.DOMAIN))
}