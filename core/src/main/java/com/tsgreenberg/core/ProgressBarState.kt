package com.tsgreenberg.core

sealed class ProgressBarState{
    object Start: ProgressBarState()
    object Loading: ProgressBarState()
    object Idle: ProgressBarState()
}