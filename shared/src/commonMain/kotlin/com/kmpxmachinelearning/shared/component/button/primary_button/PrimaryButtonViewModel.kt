package com.kmpxmachinelearning.shared.component.button.primary_button

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PrimaryButtonViewModel: ViewModel() {

    private val _state = MutableStateFlow(PrimaryButtonState())
    val state: StateFlow<PrimaryButtonState> = _state

    fun setPressed(v: Boolean) {
        _state.update { it.copy(isPressed = v) }
    }
}