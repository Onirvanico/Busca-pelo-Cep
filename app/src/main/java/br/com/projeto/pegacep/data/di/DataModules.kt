package br.com.projeto.pegacep.data.di

import br.com.projeto.pegacep.data.repository.CepRepository
import br.com.projeto.pegacep.data.repository.CepRepositoryImpl
import br.com.projeto.pegacep.data.services.CepService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {
    const val BASE_URL =  "https://cep.awesomeapi.com.br/"

    fun load() {
        loadKoinModules(networkModule()+ repositoryModule())
    }

    fun networkModule(): Module {

        return module {
            single {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            }

            single { GsonConverterFactory.create(GsonBuilder().create()) }

            single { createService<CepService>(get(), get()) }
        }
    }

    private fun repositoryModule(): Module {
        return module {
            single<CepRepository> { CepRepositoryImpl(get()) }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {

       return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()
            .create(T::class.java)
    }
}