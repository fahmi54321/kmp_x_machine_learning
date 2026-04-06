package com.example.kmpxmachinelearning

import androidx.compose.ui.window.ComposeUIViewController
import com.kmpxmachinelearning.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure =  {initializeKoin()  }
) { App() }