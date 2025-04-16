package com.newton.auth.presentation.viewModel

import androidx.lifecycle.*
import com.newton.auth.presentation.event.*
import com.newton.auth.presentation.state.*
import com.newton.prefs.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import javax.inject.*

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