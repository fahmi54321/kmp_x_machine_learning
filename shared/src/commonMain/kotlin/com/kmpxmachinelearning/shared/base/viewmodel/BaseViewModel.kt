package com.kmpxmachinelearning.shared.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpxmachinelearning.shared.core.app.AppState
import com.kmpxmachinelearning.shared.core.error.TimeoutFailure
import com.kmpxmachinelearning.shared.core.network.RequestState
import com.kmpxmachinelearning.shared.core.service.CancelManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseViewModel(
    protected val appState: AppState,
    protected val cancelManager: CancelManager,
) : ViewModel() {
    private var currentJob: Job? = null

    protected fun <T> safeRequest(
        request: suspend () -> Flow<RequestState<T>>,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit = {
            appState.showError(it)
        },
        onTimeout: (String, (() -> Unit)?) -> Unit = { msg, retry ->
            appState.showTimeout(msg, retry)
        }
    ) {

        // cancel previous request
        currentJob?.cancel()

        currentJob = viewModelScope.launch {

            // 🔥 register cancel ke global
            cancelManager.register {
                cancelRequest()
            }

            request().collect { result ->

                when (result) {

                    is RequestState.Loading -> {
                        appState.showLoading()
                    }

                    is RequestState.Success -> {
                        appState.hideLoading()
                        cancelManager.clear()
                        onSuccess(result.getSuccessData())
                    }

                    is RequestState.ErrorV2 -> {
                        appState.hideLoading()
                        cancelManager.clear()

                        when (result.failure) {
                            is TimeoutFailure -> {
                                onTimeout(
                                    result.getErrorV2Message(),
                                    null
                                )
                            }

                            else -> {
                                onError(result.getErrorV2Message())
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    fun cancelRequest() {
        println("CANCEL BASE VM")
        currentJob?.cancel()
        appState.hideLoading()
        cancelManager.clear()
    }
}