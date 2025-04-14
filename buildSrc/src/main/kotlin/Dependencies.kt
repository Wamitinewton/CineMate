object Dependencies {
    object Hilt {
        const val ANDROID = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val AGP = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
        const val NAVIGATION =
            "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    }

    object Navigation {
        const val COMPOSE = "androidx.navigation:navigation-compose:${Versions.composeNav}"
    }

    object ConstraintLayout {
        const val LAYOUT = "androidx.constraintlayout:constraintlayout:2.2.0"
        const val COMPOSE = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    }


    object Room {
        const val RUNTIME = "androidx.room:room-runtime:${Versions.room}"
        const val COMPILER = "androidx.room:room-compiler:${Versions.room}"
        const val KTX = "androidx.room:room-ktx:${Versions.room}"
    }

    object Material {
        const val EXTENDED_ICONS = "androidx.compose.material:material-icons-extended:1.7.7"
    }

    object Work {
        const val RUNTIME = "androidx.work:work-runtime-ktx:${Versions.workRuntimeKtx}"
        const val HILT_WORKER = "androidx.hilt:hilt-work:${Versions.hiltNavigationCompose}"
    }

    object Logging {
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Coil {
        const val compose = "io.coil-kt.coil3:coil-compose:3.1.0"
        const val network = "io.coil-kt.coil3:coil-network-okhttp:3.1.0"
    }

    object Lottie {
        const val compose = "com.airbnb.android:lottie-compose:5.0.3"
    }

    object Paging {
        const val runtime = "androidx.paging:paging-runtime-ktx:3.1.1"
        const val compose = "androidx.paging:paging-compose:1.0.0-alpha18"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:4.12.0"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
        const val retrofit2KotlinxConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit2KotlinxSerializationConverter}"
    }

    object Kotlinx {
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinXSerialization}"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.6.2"
    }


    object Accompanist {
        const val systemUi = "com.google.accompanist:accompanist-systemuicontroller:0.24.10-beta"
        const val pager = "com.google.accompanist:accompanist-pager:0.24.10-beta"
        const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:0.18.0"
        const val permissions = "com.google.accompanist:accompanist-permissions:0.32.0"
    }
}
