package br.com.projeto.pegacep.presentation.di

import br.com.projeto.pegacep.data.repository.CepRepositoryImpl
import br.com.projeto.pegacep.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModules {

    fun load() {
        loadKoinModules(mainViewModule())
    }

    private fun mainViewModule(): Module {
        return module {
            viewModel { MainViewModel(get()) }
        }
    }
}