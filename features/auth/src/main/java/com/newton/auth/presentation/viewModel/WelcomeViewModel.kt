package com.newton.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.event.WelcomeEvent
import com.newton.auth.presentation.event.WelcomeNavigationEvent
import com.newton.auth.presentation.state.WelcomeState
import com.newton.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WelcomeState())
    val state: StateFlow<WelcomeState> = _state.asStateFlow()

    private val _navigationEvents = Channel<WelcomeNavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    init {
        val savePrefs = prefsRepository.getAllowAdultContent()
        _state.update { it.copy(allowAdultContent = savePrefs) }
    }

    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.Continue -> {
                savePrefsAndNavigate()
            }

            is WelcomeEvent.ToggleAdultContent -> {
                _state.update { it.copy(allowAdultContent = event.allow) }
            }
        }
    }


    private fun savePrefsAndNavigate() {
        viewModelScope.launch {
            prefsRepository.setAllowAdultContent(_state.value.allowAdultContent)

            prefsRepository.setUserOnboardingStatus(true)
            prefsRepository.setPreferencesCompleted(true)
            _navigationEvents.send(WelcomeNavigationEvent.NavigateToHome)
            navigateToHome()
        }
    }


    private suspend fun navigateToHome() {
        _navigationEvents.send(WelcomeNavigationEvent.NavigateToHome)
    }
}