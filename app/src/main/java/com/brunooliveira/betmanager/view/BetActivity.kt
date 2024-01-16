package com.brunooliveira.betmanager.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.brunooliveira.betmanager.R
import com.brunooliveira.betmanager.model.Bet
import com.brunooliveira.betmanager.util.getActivity
import com.brunooliveira.betmanager.view.ui.theme.BetManagerTheme
import com.brunooliveira.betmanager.view.ui.widget.DecimalTextField
import com.brunooliveira.betmanager.view.ui.widget.DropDownList
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
                MainContent(
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
                MainContent(
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
                    if (result != null) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = ContextCompat.getString(mContext, result),
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else {
                        viewModel.addBet()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = ContextCompat.getString(
                                    mContext,
                                    R.string.aposta_cadastrada_com_sucesso
                                ),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(
    innerPadding: PaddingValues,
    bet: Bet,
    updateTitle: (String) -> Unit,
    updateStatus: (String) -> Unit,
    updateBetHouse: (String) -> Unit,
    updateDescription: (String) -> Unit,
    updateStake: (Double) -> Unit,
    updateOdds: (Double) -> Unit,
    updateSport: (String) -> Unit,
    addBet: () -> Unit
) {
    var statusOpened by rememberSaveable { mutableStateOf(false) }
    var betHouseOpened by rememberSaveable { mutableStateOf(false) }
    var sportOpened by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = bet.title,
            onValueChange = updateTitle,
            label = { Text(stringResource(R.string.titulo)) },
            singleLine = true
        )
        DropDownList(
            modifier = Modifier.padding(top = 8.dp),
            list = listOf(
                stringResource(R.string.em_progresso),
                stringResource(R.string.green),
                stringResource(R.string.red),
                stringResource(R.string.meio_green),
                stringResource(R.string.meio_red),
                stringResource(R.string.reembolso)
            ),
            value = bet.status,
            onValueChange = updateStatus,
            opened = statusOpened,
            onOpenedChange = { statusOpened = it },
            label = stringResource(R.string.status)
        )
        DropDownList(
            modifier = Modifier.padding(top = 8.dp),
            list = listOf(
                stringResource(R.string.bet365),
                stringResource(R.string.betano)
            ),
            value = bet.betHouse,
            onValueChange = updateBetHouse,
            opened = betHouseOpened,
            onOpenedChange = { betHouseOpened = it },
            label = stringResource(R.string.casa_de_aposta)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = bet.description,
            onValueChange = updateDescription,
            label = { Text(stringResource(R.string.descricao)) },
            singleLine = true
        )
        DecimalTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = bet.stake.toString(),
            onValueChange = { updateStake(it.toDouble()) },
            label = stringResource(R.string.stake)
        )
        DecimalTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = bet.odds.toString(),
            onValueChange = { updateOdds(it.toDouble()) },
            label = stringResource(R.string.odds)
        )
        DropDownList(
            modifier = Modifier.padding(top = 8.dp),
            list = listOf(
                stringResource(R.string.futebol),
                stringResource(R.string.basquete)
            ),
            value = bet.sport,
            onValueChange = updateSport,
            opened = sportOpened,
            onOpenedChange = { sportOpened = it },
            label = stringResource(R.string.esporte)
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = addBet,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(R.string.adicionar_aposta),
                    fontSize = 18.sp
                )
            }
        }
    }
}