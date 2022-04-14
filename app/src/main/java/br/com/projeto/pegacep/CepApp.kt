package br.com.projeto.pegacep

import android.app.Application
import br.com.projeto.pegacep.data.di.DataModules
import br.com.projeto.pegacep.presentation.di.PresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CepApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CepApp)
        }
            DataModules.load()
            PresentationModules.load()
    }
}