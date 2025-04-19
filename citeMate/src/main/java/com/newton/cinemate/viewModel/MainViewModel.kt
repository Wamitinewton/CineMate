package com.newton.cinemate.viewModel

import androidx.lifecycle.*
import com.newton.navigation.*
import com.newton.prefs.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
) : ViewModel() {
    private val _startDestination = MutableStateFlow(NavigationSubgraphRoutes.Auth.route)
    val startDestination: StateFlow<String> = _startDestination

    init {
        viewModelScope.launch {
            val hasCompletedPrefs = prefsRepository.hasCompletedPreferences()
            _startDestination.value =
                if (!hasCompletedPrefs) {
                    NavigationSubgraphRoutes.Auth.route
                } else {
                    NavigationSubgraphRoutes.Trending.route
                }
        }
    }
}
