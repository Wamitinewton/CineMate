plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}

android {
    namespace = "com.newton.auth"
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

    //navigation
    implementation(Dependencies.Hilt.NAVIGATION)
    implementation(Dependencies.Navigation.COMPOSE)

    //hilt
    implementation(Dependencies.Hilt.ANDROID)
    ksp(Dependencies.Hilt.COMPILER)

    implementation(Dependencies.Coil.network)
    implementation(Dependencies.Coil.compose)

    implementation(Dependencies.Kotlinx.serialization)

    implementation(Dependencies.CUSTOM_TABS)

    implementation(Dependencies.Logging.TIMBER)
    implementation(Dependencies.Material.EXTENDED_ICONS)

    //modules
    implementation(project(Modules.NAVIGATION))
    implementation(project(Modules.SHARED_UI))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.CORE))
    implementation(project(Modules.PREFS))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATABASE))
}