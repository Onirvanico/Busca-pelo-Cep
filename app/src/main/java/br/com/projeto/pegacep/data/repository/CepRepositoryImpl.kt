package br.com.projeto.pegacep.data.repository

import br.com.projeto.pegacep.core.exceptions.RemoteException
import br.com.projeto.pegacep.data.services.CepService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CepRepositoryImpl(private val cepService: CepService) : CepRepository {
    companion object {
        const val NOT_FOUND = 404
    }

    override suspend fun getData(value: String) = flow {

        try {
            val fetchData = cepService.fetchData(value)
            emit(fetchData)

        } catch (ex: HttpException) {
            if (ex.code() == NOT_FOUND) throw RemoteException("Não foi possível encontrar endereço com o cep informado!")

        } catch (ex: Exception) {
            ex.printStackTrace()
            throw RemoteException("Houve um erro, tente novamente!")

        }
    }
}