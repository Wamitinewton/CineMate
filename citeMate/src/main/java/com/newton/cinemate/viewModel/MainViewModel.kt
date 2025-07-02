package com.newton.cinemate.viewModel

import androidx.lifecycle.*
import com.newton.domain.repository.TmdbAuthRepository
import com.newton.navigation.*
import com.newton.prefs.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val tmdbAuthRepository: TmdbAuthRepository
) : ViewModel() {
    private val _startDestination = MutableStateFlow(NavigationSubgraphRoutes.Auth.route)
    val startDestination: StateFlow<String> = _startDestination

    init {
        viewModelScope.launch {
            val hasCompletedPrefs = prefsRepository.hasCompletedPreferences()
            val isAuthenticated = tmdbAuthRepository.isUserAuthenticated()

            _startDestination.value = when {
                !hasCompletedPrefs -> NavigationSubgraphRoutes.Auth.route
                isAuthenticated -> NavigationSubgraphRoutes.Trending.route
                else -> NavigationSubgraphRoutes.Auth.route
            }
        }
    }
}
