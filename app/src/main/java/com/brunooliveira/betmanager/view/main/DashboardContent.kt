package com.brunooliveira.betmanager.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brunooliveira.betmanager.util.BetAnalytics
import com.brunooliveira.betmanager.util.Bets
import com.brunooliveira.betmanager.view.ui.widget.DropDownList

@Composable
fun DashboardContent(
    innerPadding: PaddingValues,
    bets: Bets
) {
    var analytics by remember { mutableStateOf("Ganhos/Perdas (Últimos 30 dias)") }
    var analyticsOpened by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(
            top = innerPadding.calculateTopPadding().plus(12.dp),
            bottom = innerPadding.calculateBottomPadding(),
            start = 12.dp,
            end = 12.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ElevatedCard(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Apostas",
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    "${bets.size}",
                    fontSize = 20.sp,
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
                    "ROI",
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    BetAnalytics.totalROI(bets),
                    fontSize = 20.sp,
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
                    "Resultado",
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    BetAnalytics.totalResult(bets),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )
            }
        }
        DropDownList(
            modifier = Modifier.padding(top = 8.dp),
            list = listOf(
                "Ganhos/Perdas (Últimos 30 dias)",
                "Resumo do resultado",
                "Resultados mensais",
                "Estatísticas de ROI",
                "Estatísticas por odd",
                "Estatísticas por esporte"
            ),
            value = analytics,
            onValueChange = { analytics = it },
            opened = analyticsOpened,
            onOpenedChange = { analyticsOpened = it },
            label = "Análise"
        )
    }
}