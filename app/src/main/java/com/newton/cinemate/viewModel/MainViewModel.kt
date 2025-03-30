package com.newton.cinemate.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
): ViewModel() {
    private val _startDestination = MutableStateFlow(NavigationSubgraphRoutes.Auth.route)
    val startDestination: StateFlow<String> = _startDestination

    init {
        viewModelScope.launch {
            val onboardingStatus = prefsRepository.getUserOnboardingStatus()
            val isGuest = prefsRepository.isGuestUser()
            val hasCompletedPrefs = prefsRepository.hasCompletedPreferences()

            _startDestination.value = if (onboardingStatus) {
                if (isGuest && !hasCompletedPrefs) {
                    NavigationSubgraphRoutes.Auth.route
                } else {
                    NavigationSubgraphRoutes.Trending.route
                }
            } else {
                NavigationSubgraphRoutes.Auth.route
            }
        }
    }
}