package com.newton.cinemate.di

import android.content.*
import androidx.credentials.*
import com.google.firebase.*
import com.google.firebase.auth.*
import com.google.firebase.firestore.*
import com.newton.cinemate.BuildConfig
import com.newton.network.data.interceptor.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import okhttp3.*
import okhttp3.logging.*
import retrofit2.*
import retrofit2.converter.gson.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private const val API_KEY = BuildConfig.TMDB_API
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor(API_KEY))
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideCineMateApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): CredentialManager {
        return CredentialManager.create(context)
    }

}