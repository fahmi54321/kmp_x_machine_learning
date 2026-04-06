package com.kmpxmachinelearning.shared.core.service

class CancelManager {

    private var currentCancel: (() -> Unit)? = null

    fun register(cancel: () -> Unit) {
        currentCancel = cancel
    }

    fun cancel() {
        println("GLOBAL CANCEL TRIGGERED")
        currentCancel?.invoke()
        currentCancel = null
    }

    fun clear() {
        currentCancel = null
    }
}