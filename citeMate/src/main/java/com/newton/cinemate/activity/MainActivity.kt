package com.newton.cinemate.activity

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import com.newton.cinemate.navigation.*
import dagger.hilt.android.*
import javax.inject.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationSubGraphs: NavigationSubGraphs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootScreen(navigationSubGraphs)
        }
    }
}
