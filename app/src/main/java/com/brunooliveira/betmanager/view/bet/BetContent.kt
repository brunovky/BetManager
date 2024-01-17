package com.brunooliveira.betmanager.view.bet

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brunooliveira.betmanager.R
import com.brunooliveira.betmanager.model.Bet
import com.brunooliveira.betmanager.view.ui.widget.DecimalTextField
import com.brunooliveira.betmanager.view.ui.widget.DropDownList

@Composable
fun BetContent(
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