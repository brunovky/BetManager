package com.brunooliveira.betmanager.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brunooliveira.betmanager.R
import com.brunooliveira.betmanager.util.Bets
import com.brunooliveira.betmanager.util.format
import com.brunooliveira.betmanager.util.toDate

@Composable
fun MainContent(
    innerPadding: PaddingValues,
    bets: Bets
) {
    if (bets.isNotEmpty()) {
        BetListView(
            innerPadding,
            bets
        )
    } else {
        BetPlaceholderView(
            innerPadding
        )
    }
}

@Composable
fun BetPlaceholderView(
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text(
                stringResource(R.string.emoji_triste),
                fontSize = 64.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                stringResource(R.string.placeholder_main),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
fun BetListView(
    innerPadding: PaddingValues,
    bets: Bets
) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
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
                            stringResource(R.string.casa),
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp, top = 16.dp)
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
                            stringResource(R.string.stake),
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp, top = 16.dp)
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
                            stringResource(R.string.odds),
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp, top = 16.dp)
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
                            stringResource(R.string.status),
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp, top = 16.dp)
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
                .padding(top = 20.dp, bottom = 18.dp)
                .size(16.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.Red)
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