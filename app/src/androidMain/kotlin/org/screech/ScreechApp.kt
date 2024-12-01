package org.screech

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.screech.login.presentation.LoginViewModel

class ScreechApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(module {
                single { LoginViewModel() }
            })
        }
    }
}