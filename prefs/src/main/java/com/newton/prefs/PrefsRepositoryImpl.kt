package com.newton.prefs

import javax.inject.*

@Singleton
class PrefsRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : PrefsRepository {
    override fun getAllowAdultContent(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_ALLOW_ADULT_CONTENT)
    }

    override fun setAllowAdultContent(allow: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_ALLOW_ADULT_CONTENT, allow)
    }

    override fun getUserOnboardingStatus(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_USER_ONBOARDED)
    }

    override fun setUserOnboardingStatus(completed: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_USER_ONBOARDED, completed)
    }

    override fun setGuestUser(isGuest: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_IS_GUEST_USER, isGuest)
    }

    override fun isGuestUser(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_IS_GUEST_USER, false)
    }

    override fun hasCompletedPreferences(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_PREFERENCES_COMPLETED, false)
    }

    override fun setPreferencesCompleted(completed: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_PREFERENCES_COMPLETED, completed)
    }
}