package br.com.projeto.pegacep.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.projeto.pegacep.data.model.Content
import br.com.projeto.pegacep.data.repository.CepRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CepRepository): ViewModel() {

    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun retrieveCep(cep: String) {
        viewModelScope.launch {
            repository.getData(cep)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Load
                }
                .catch {
                    _state.value = State.Failure(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    sealed class State {
        object Load : State()
        data class Success(val content: Content) : State()
        data class Failure(val ex: Throwable) : State()
     }

}