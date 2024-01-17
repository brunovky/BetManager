package com.brunooliveira.betmanager.view.bet

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.brunooliveira.betmanager.R
import com.brunooliveira.betmanager.model.Bet
import com.brunooliveira.betmanager.util.getActivity
import com.brunooliveira.betmanager.view.ui.theme.BetManagerTheme
import com.brunooliveira.betmanager.viewmodel.BetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BetScreenPreview() {
    val mContext = LocalContext.current

    BetManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.secondary
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    mContext.getActivity()?.finish()
                                }) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(stringResource(R.string.criar_aposta))
                        }
                    )
                }
            ) { innerPadding ->
                BetContent(
                    innerPadding,
                    Bet.NEW,
                    {},
                    {},
                    {},
                    {},
                    {},
                    {},
                    {},
                    {}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetScreen(
    viewModel: BetViewModel = hiltViewModel()
) {
    val mContext = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BetManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
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
                                    mContext.getActivity()?.finish()
                                }) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(stringResource(R.string.criar_aposta))
                        }
                    )
                }
            ) { innerPadding ->
                BetContent(
                    innerPadding,
                    viewModel.bet,
                    viewModel::updateTitle,
                    viewModel::updateStatus,
                    viewModel::updateBetHouse,
                    viewModel::updateDescription,
                    viewModel::updateStake,
                    viewModel::updateOdds,
                    viewModel::updateSport
                ) {
                    val result = viewModel.validateFields()
                    if (result == null) {
                        viewModel.addBet()
                    }
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = ContextCompat.getString(mContext, result ?: R.string.aposta_cadastrada_com_sucesso),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }
}