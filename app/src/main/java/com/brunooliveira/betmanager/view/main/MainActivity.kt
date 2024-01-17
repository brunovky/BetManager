package com.brunooliveira.betmanager.view.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brunooliveira.betmanager.R
import com.brunooliveira.betmanager.model.Bet
import com.brunooliveira.betmanager.util.Bets
import com.brunooliveira.betmanager.view.ui.theme.BetManagerTheme
import com.brunooliveira.betmanager.viewmodel.DASHBOARD_CONTENT
import com.brunooliveira.betmanager.viewmodel.MAIN_CONTENT
import com.brunooliveira.betmanager.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

class BetPreviewParameterProvider: PreviewParameterProvider<Bets> {

    override val values: Sequence<Bets> = sequenceOf(
        listOf(
            Bet(1, "Celtics v Pacers", "Em progresso", "Bet365", "Tatum DD", 0.75, 2.35, "Basquete", "15/01/2024 10:00:00"),
            Bet(2, "Real Madrid v Barcelona", "Green", "Bet365", "ML Real Madrid", 1.0, 1.8, "Futebol", "15/01/2024 12:00:00"),
            Bet(3, "76ers v Nuggets", "Red", "Bet365", "Embiid TD", 0.5, 3.75, "Basquete", "16/01/2024 21:00:00"),
            Bet(4, "Liverpool v Man City", "Reembolso", "Bet365", "Salah 1+ SHO", 1.5, 1.4, "Futebol", "20/01/2024 13:00:00")
        ),
        emptyList()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview(
    @PreviewParameter(BetPreviewParameterProvider::class) bets: Bets
) {
    val mContext = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentContent = remember { mutableIntStateOf(MAIN_CONTENT) }

    BetManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        NavigationDrawerItem(
                            modifier = Modifier.padding(12.dp),
                            label = { Text("Dashboard") },
                            icon = { Icon(Icons.Filled.List, contentDescription = null) },
                            selected = currentContent.intValue == DASHBOARD_CONTENT,
                            onClick = {
                                currentContent.intValue = DASHBOARD_CONTENT
                                scope.launch { drawerState.close() }
                            }
                        )
                        NavigationDrawerItem(
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                            label = { Text("Apostas") },
                            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = null) },
                            selected = currentContent.intValue == MAIN_CONTENT,
                            onClick = {
                                currentContent.intValue = MAIN_CONTENT
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.secondary
                            ),
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }) {
                                    Icon(
                                        Icons.Filled.Menu,
                                        contentDescription = null
                                    )
                                }
                            },
                            title = {
                                Text(stringResource(R.string.app_name))
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = {

                            }) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = null
                            )
                        }
                    }
                ) { innerPadding ->
                    when (currentContent.intValue) {
                        MAIN_CONTENT -> MainContent(
                            innerPadding,
                            bets
                        )
                        DASHBOARD_CONTENT -> DashboardContent(
                            innerPadding,
                            bets
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val mContext = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val bets by viewModel.bets.collectAsState(initial = emptyList())

    BetManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {

                    }
                }) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.secondary
                            ),
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }) {
                                    Icon(
                                        Icons.Filled.Menu,
                                        contentDescription = null
                                    )
                                }
                            },
                            title = {
                                Text(stringResource(R.string.app_name))
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {

                            }) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = null
                            )
                        }
                    }
                ) { innerPadding ->
                    when (viewModel.currentContent.intValue) {
                        MAIN_CONTENT -> MainContent(
                            innerPadding,
                            bets
                        )
                        DASHBOARD_CONTENT -> DashboardContent(
                            innerPadding,
                            bets
                        )
                    }
                }
            }
        }
    }
}