package com.newton.auth.presentation.manager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.*
import androidx.core.content.ContextCompat
import com.newton.auth.R
import com.newton.core.utils.TmdbConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri
import androidx.core.graphics.createBitmap

@Singleton
class TmdbCustomTabsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var customTabsClient: CustomTabsClient? = null
    private var customTabsSession: CustomTabsSession? = null
    private var serviceConnected = false

    private val customTabsServiceConnection = object : CustomTabsServiceConnection() {
        override fun onCustomTabsServiceConnected(
            componentName: android.content.ComponentName,
            client: CustomTabsClient
        ) {
            customTabsClient = client
            client.warmup(0L)
            customTabsSession = client.newSession(null)
            serviceConnected = true
            Timber.d("CustomTabs service connected")
        }

        override fun onServiceDisconnected(componentName: android.content.ComponentName) {
            customTabsClient = null
            customTabsSession = null
            serviceConnected = false
            Timber.d("CustomTabs service disconnected")
        }
    }

    init {
        bindCustomTabsService()
    }

    private fun bindCustomTabsService() {
        val packageName = getCustomTabsPackageName()
        if (packageName != null) {
            CustomTabsClient.bindCustomTabsService(
                context,
                packageName,
                customTabsServiceConnection
            )
        }
    }

    private fun getCustomTabsPackageName(): String? {
        val browsers = CustomTabsClient.getPackageName(context, null)
        return browsers
    }

    fun launchTmdbAuth(requestToken: String): Intent? {
        return try {
            val authUrl = TmdbConstants.getAuthUrl(requestToken)
            val intent = createCustomTabsIntent(authUrl)

            customTabsSession?.mayLaunchUrl(authUrl.toUri(), null, null)

            intent
        } catch (e: Exception) {
            Timber.e(e, "Failed to create CustomTabs intent")
            if (e is CancellationException) throw e
            null
        }
    }

    private fun createCustomTabsIntent(url: String): Intent {
        val builder = CustomTabsIntent.Builder(customTabsSession)
            .setShowTitle(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .setStartAnimations(
                context,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .setExitAnimations(
                context,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .setToolbarColor(ContextCompat.getColor(context, R.color.tmdb_primary))
            .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.tmdb_primary_dark))
            .setNavigationBarColor(ContextCompat.getColor(context, R.color.tmdb_primary_dark))
            .setCloseButtonIcon(
                ContextCompat.getDrawable(context, R.drawable.baseline_arrow_back_24)!!.apply {
                    setTint(Color.WHITE)
                }.let { drawable ->
                    val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
                    val canvas = android.graphics.Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    bitmap
                }
            )

        val customTabsIntent = builder.build()
        customTabsIntent.intent.data = url.toUri()

        return customTabsIntent.intent
    }

    fun unbindService() {
        try {
            context.unbindService(customTabsServiceConnection)
        } catch (e: Exception) {
            Timber.w(e, "Failed to unbind CustomTabs service")
        }
    }
}
