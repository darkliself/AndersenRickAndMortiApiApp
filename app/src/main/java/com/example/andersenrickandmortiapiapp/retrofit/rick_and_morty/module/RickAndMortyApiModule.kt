package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.module

import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.api.RickAndMortyApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
object RickAndMortyApiModel {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): RickAndMortyApiInterface {
        return builder.build().create(RickAndMortyApiInterface::class.java)
    }
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }
}
