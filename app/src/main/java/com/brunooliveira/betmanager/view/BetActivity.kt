package com.brunooliveira.betmanager.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brunooliveira.betmanager.view.ui.theme.BetManagerTheme
import com.brunooliveira.betmanager.view.ui.widget.DecimalTextField
import com.brunooliveira.betmanager.view.ui.widget.DropDownList
import com.brunooliveira.betmanager.viewmodel.BetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Screen(
    viewModel: BetViewModel = hiltViewModel()
) {
    val bet = viewModel.bet
    var statusOpened by rememberSaveable { mutableStateOf(false) }
    var betHouseOpened by rememberSaveable { mutableStateOf(false) }
    var sportOpened by rememberSaveable { mutableStateOf(false) }

    val mContext = LocalContext.current

    BetManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = bet.title,
                    onValueChange = viewModel::updateTitle,
                    label = { Text("Título") },
                    singleLine = true
                )
                DropDownList(
                    modifier = Modifier.padding(top = 8.dp),
                    list = listOf(
                        "Em progresso",
                        "Green",
                        "Red",
                        "Meio green",
                        "Meio red",
                        "Reembolso"
                    ),
                    value = bet.status,
                    onValueChange = viewModel::updateStatus,
                    opened = statusOpened,
                    onOpenedChange = { statusOpened = it },
                    label = "Status"
                )
                DropDownList(
                    modifier = Modifier.padding(top = 8.dp),
                    list = listOf(
                        "Bet365",
                        "Betano"
                    ),
                    value = bet.betHouse,
                    onValueChange = viewModel::updateBetHouse,
                    opened = betHouseOpened,
                    onOpenedChange = { betHouseOpened = it },
                    label = "Casa de aposta"
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = bet.description,
                    onValueChange = viewModel::updateDescription,
                    label = { Text("Descrição") },
                    singleLine = true
                )
                DecimalTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = bet.stake.toString(),
                    onValueChange = { viewModel.updateStake(it.toDouble()) },
                    label = "Stake"
                )
                DecimalTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = bet.odds.toString(),
                    onValueChange = { viewModel.updateOdds(it.toDouble()) },
                    label = "Odds"
                )
                DropDownList(
                    modifier = Modifier.padding(top = 8.dp),
                    list = listOf(
                        "Futebol",
                        "Basquete"
                    ),
                    value = bet.sport,
                    onValueChange = viewModel::updateSport,
                    opened = sportOpened,
                    onOpenedChange = { sportOpened = it },
                    label = "Esporte"
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            viewModel.addBet()
                            Toast.makeText(mContext, "Aposta cadastrada com sucesso!", Toast.LENGTH_LONG).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            "Adicionar aposta",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}