package com.scoqu.lasw.egasy.di

import android.app.Application
import androidx.room.Room
import com.scoqu.lasw.egasy.common.Constants.BASE_CITY_URL
import com.scoqu.lasw.egasy.common.Constants.BASE_WEATHER_URL
import com.scoqu.lasw.egasy.common.Constants.DATABASE_NAME
import com.scoqu.lasw.egasy.data.local.AppDatabase
import com.scoqu.lasw.egasy.data.remote.CityApiInterface
import com.scoqu.lasw.egasy.data.remote.WeatherApiInterface
import com.scoqu.lasw.egasy.data.repository.CityRepositoryImpl
import com.scoqu.lasw.egasy.data.repository.WeatherRepositoryImpl
import com.scoqu.lasw.egasy.domain.repository.CityRepository
import com.scoqu.lasw.egasy.domain.repository.WeatherRepository
import com.scoqu.lasw.egasy.domain.use_case.city.CityInteractor
import com.scoqu.lasw.egasy.domain.use_case.city.CityInteractorImpl
import com.scoqu.lasw.egasy.domain.use_case.user.UserInteractor
import com.scoqu.lasw.egasy.domain.use_case.user.UserInteractorImpl
import com.scoqu.lasw.egasy.domain.use_case.weather.WeatherInteractor
import com.scoqu.lasw.egasy.domain.use_case.weather.WeatherInteractorImpl
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

    @Provides
    @Singleton
    fun provideWeatherApi() : WeatherApiInterface{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideCityApi() : CityApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_CITY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CityApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) : AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCityRepository(api: CityApiInterface, db: AppDatabase) : CityRepository {
        return CityRepositoryImpl(api, db.appDao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiInterface, db: AppDatabase) : WeatherRepository {
        return WeatherRepositoryImpl(api, db.appDao)
    }

    @Provides
    @Singleton
    fun provideUserInteractor() : UserInteractor {
        return UserInteractorImpl()
    }

    @Provides
    @Singleton
    fun provideWeatherInteractor(repository: WeatherRepository) : WeatherInteractor {
        return WeatherInteractorImpl(repository)
    }

    @Provides
    @Singleton
    fun provideCityInteractor(repository: CityRepository) : CityInteractor {
        return CityInteractorImpl(repository)
    }
}

