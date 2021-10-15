package ir.erfan_mh_at.test_ba_salam.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.erfan_mh_at.test_ba_salam.common.Constants.BASE_URL
import ir.erfan_mh_at.test_ba_salam.common.Constants.BA_SALAM_DATABASE_NAME
import ir.erfan_mh_at.test_ba_salam.data.database.AnimalAndFlowerDAO
import ir.erfan_mh_at.test_ba_salam.data.database.BaSalamDatabase
import ir.erfan_mh_at.test_ba_salam.data.remote.BaSalamApi
import ir.erfan_mh_at.test_ba_salam.data.repository.MainRepositoryImpl
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
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
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): BaSalamApi = retrofit.create(BaSalamApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BaSalamDatabase = Room.databaseBuilder(
        context,
        BaSalamDatabase::class.java,
        BA_SALAM_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideAnimalAndFlowerDAO(db: BaSalamDatabase) = db.getAnimalAndFlowerDAO()

    @Provides
    @Singleton
    fun provideMainRepository(api: BaSalamApi, animalAndFlowerDAO: AnimalAndFlowerDAO): MainRepository =
        MainRepositoryImpl(api, animalAndFlowerDAO)
}