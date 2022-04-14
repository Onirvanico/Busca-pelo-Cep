package br.com.projeto.pegacep.data.repository

import br.com.projeto.pegacep.data.model.Content
import kotlinx.coroutines.flow.Flow

interface CepRepository {

    suspend fun getData(value: String): Flow<Content>
}