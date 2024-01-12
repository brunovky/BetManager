package com.brunooliveira.betmanager.view.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DropDownList(
    modifier: Modifier = Modifier,
    list: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    opened: Boolean,
    onOpenedChange: (Boolean) -> Unit,
    label: String
) {
    Box(
        modifier = modifier
    ) {
        Column {
            OutlinedTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = opened,
                onDismissRequest = { onOpenedChange(false) },
            ) {
                list.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it, modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.Start)) },
                        onClick = {
                            onOpenedChange(false)
                            onValueChange(it)
                        }
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { onOpenedChange(true) }
                )
        )
        Icon(
            if (opened) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 4.dp, top = 4.dp)
        )
    }
}