package com.kmpxmachinelearning.shared.core.error

data class UiError(
    val message: String,
    val onRetry: (() -> Unit)? = null
)