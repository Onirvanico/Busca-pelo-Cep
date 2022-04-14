package br.com.projeto.pegacep.data.services

import br.com.projeto.pegacep.data.model.Content
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {

    @GET("json/{value}")
    suspend fun fetchData(@Path("value") value: String): Content
}