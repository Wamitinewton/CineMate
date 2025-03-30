package com.newton.prefs

interface PrefsRepository {
    fun getAllowAdultContent(): Boolean
    fun setAllowAdultContent(allow: Boolean)
    fun getUserOnboardingStatus(): Boolean
    fun setUserOnboardingStatus(completed: Boolean)
    fun setGuestUser(isGuest: Boolean)
    fun isGuestUser(): Boolean
    fun hasCompletedPreferences(): Boolean
    fun setPreferencesCompleted(completed: Boolean)
}