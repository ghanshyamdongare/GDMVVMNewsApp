package com.app.employeeapp.di

import androidx.room.Room
import com.androiddevs.gdmvvmnewsapp.MainApp
import com.androiddevs.gdmvvmnewsapp.api.NewsAPI
import com.androiddevs.gdmvvmnewsapp.db.ArticleDatabase
import com.androiddevs.gdmvvmnewsapp.repository.NewsRepository
import com.androiddevs.gdmvvmnewsapp.repository.NewsRepositoryImpl
import com.androiddevs.gdmvvmnewsapp.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonCreator(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(gsonCreator: Gson): Retrofit.Builder {
        val logging = HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonCreator))
            .client(client)
    }


    @Singleton
    @Provides
    fun provideApiService(retrofitInstance: Retrofit.Builder): NewsAPI {
        return retrofitInstance
            .build()
            .create(NewsAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideArticleDatabase(): ArticleDatabase {
        return Room.databaseBuilder(
            MainApp.appInstance,
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNewsRepository(
        apiService: NewsAPI, database: ArticleDatabase
    ) = NewsRepositoryImpl(apiService, database) as NewsRepository
}
