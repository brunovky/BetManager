package com.brunooliveira.betmanager.view.ui.widget

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun DecimalTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    val decimalFormatter = DecimalFormatter()
    OutlinedTextField(
        modifier = modifier,
        value = value,
        label = { Text(label) },
        onValueChange = {
            onValueChange(decimalFormatter.cleanup(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
        ),
        visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
    )
}