package com.brunooliveira.betmanager.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brunooliveira.betmanager.R
import com.brunooliveira.betmanager.model.Bet
import com.brunooliveira.betmanager.util.Bets
import com.brunooliveira.betmanager.util.format
import com.brunooliveira.betmanager.util.toDate
import com.brunooliveira.betmanager.view.ui.theme.BetManagerTheme
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    val mContext = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
                    }
                ) { innerPadding ->
                    MainContent(
                        innerPadding,
                        listOf(
                            Bet(1, "Celtics v Pacers", "Em progresso", "Bet365", "Tatum DD", 0.75, 2.35, "Basquete", "15/01/2024 10:00:00"),
                            Bet(2, "Real Madrid v Barcelona", "Green", "Bet365", "ML Real Madrid", 1.0, 1.8, "Futebol", "15/01/2024 12:00:00"),
                            Bet(3, "Juventus v Sassuolo", "Meio green", "Betano", "Sassuolo +1.0", 1.0, 1.57, "Futebol", "16/01/2024 08:00:00"),
                            Bet(4, "Nuggets v 76ers", "Meio red", "Bet365", "Nuggets +10.5", 1.0, 1.63, "Basquete", "16/01/2024 23:00:00"),
                            Bet(5, "Liverpool v Man City", "Reembolso", "Bet365", "Salah 1+ SHO", 1.5, 1.4, "Futebol", "20/01/2024 13:00:00")
                        )
                    )
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
                    }
                ) { innerPadding ->
                    MainContent(
                        innerPadding,
                        bets
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    innerPadding: PaddingValues,
    bets: Bets
) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
        if (bets.isNotEmpty()) {
            items(
                items = bets
            ) { bet ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            bet.date.toDate().format("dd MMM"),
                            fontSize = 14.sp
                        )
                        Text(
                            bet.date.toDate().format("HH:mm"),
                            modifier = Modifier.align(Alignment.CenterEnd),
                            fontSize = 14.sp
                        )
                    }
                    val id = "icon"
                    val text = buildAnnotatedString {
                        appendInlineContent(id, "[icon]")
                        append(" ${bet.title}")
                    }
                    val inlineContent = mapOf(
                        Pair(
                            id,
                            InlineTextContent(
                                Placeholder(
                                    width = 14.sp,
                                    height = 14.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                )
                            ) {
                                Icon(
                                    painterResource(if (bet.sport == stringResource(R.string.futebol)) R.drawable.soccer else R.drawable.basketball),
                                    contentDescription = null
                                )
                            }
                        )
                    )
                    Text(
                        text,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 20.sp,
                        maxLines = 1,
                        inlineContent = inlineContent
                    )
                    Text(
                        bet.description,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 8.dp),
                        maxLines = 2
                    )
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        ElevatedCard(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "Casa",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 12.sp
                            )
                            Text(
                                bet.betHouse,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 16.dp)
                            )
                        }
                        Spacer(
                            modifier = Modifier.weight(0.1f)
                        )
                        ElevatedCard(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "Stake",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 12.sp
                            )
                            Text(
                                bet.stake.toString(),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 16.dp)
                            )
                        }
                        Spacer(
                            modifier = Modifier.weight(0.1f)
                        )
                        ElevatedCard(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "Odds",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 12.sp
                            )
                            Text(
                                bet.odds.toString(),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 16.dp)
                            )
                        }
                        Spacer(
                            modifier = Modifier.weight(0.1f)
                        )
                        ElevatedCard(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "Status",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 12.sp
                            )
                            StatusView(
                                status = bet.status,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusView(
    status: String,
    modifier: Modifier
) {
    when (status) {
        stringResource(R.string.em_progresso) -> Icon(
            Icons.Filled.DateRange,
            contentDescription = null,
            modifier = Modifier
                .then(modifier)
                .padding(top = 16.dp, bottom = 14.dp)
        )
        stringResource(R.string.green) -> Box(
            modifier = Modifier
                .then(modifier)
                .padding(top = 20.dp, bottom = 18.dp)
                .size(16.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.Green)
        )
        stringResource(R.string.red) -> Box(
            modifier = Modifier
                .then(modifier)
                .size(16.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.Red)
                .padding(top = 20.dp, bottom = 18.dp)
        )
        stringResource(R.string.meio_green) -> Box(
            modifier = Modifier
                .then(modifier)
                .padding(top = 20.dp, bottom = 18.dp)
                .size(16.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Brush.horizontalGradient(0f to Color.Green, 100f to Color.White))
        )
        stringResource(R.string.meio_red) -> Box(
            modifier = Modifier
                .then(modifier)
                .padding(top = 20.dp, bottom = 18.dp)
                .size(16.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Brush.horizontalGradient(0f to Color.Red, 100f to Color.White))
        )
        else -> Box(
            modifier = Modifier
                .then(modifier)
                .padding(top = 20.dp, bottom = 18.dp)
                .size(16.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White)
        )
    }
}