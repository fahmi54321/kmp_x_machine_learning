package com.kmpxmachinelearning.di

import com.kmpxmachinelearning.salary.data.datasource.SalaryDataSource
import com.kmpxmachinelearning.salary.data.datasource.SalaryDataSourceImpl
import com.kmpxmachinelearning.salary.data.repository.SalaryRepositoryImpl
import com.kmpxmachinelearning.salary.domain.repository.SalaryRepository
import com.kmpxmachinelearning.salary.domain.usecase.SalaryUsecase
import com.kmpxmachinelearning.salary.domain.usecase.SalaryUsecaseImpl
import com.kmpxmachinelearning.salary.presentation.viewmodel.SalaryViewModel
import com.kmpxmachinelearning.shared.component.button.primary_button.PrimaryButtonViewModel
import com.kmpxmachinelearning.shared.core.app.AppState
import com.kmpxmachinelearning.shared.core.network.provideHttpClient
import com.kmpxmachinelearning.shared.core.service.CancelManager
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { provideHttpClient() }

    single<SalaryDataSource> {
        SalaryDataSourceImpl(get())
    }

    single<SalaryRepository> {
        SalaryRepositoryImpl(get())
    }

    single {
        AppState()
    }

    single { CancelManager() }

    factory<SalaryUsecase> {
        SalaryUsecaseImpl(get())
    }

    viewModelOf(::SalaryViewModel)
    viewModelOf(::PrimaryButtonViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
){
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}