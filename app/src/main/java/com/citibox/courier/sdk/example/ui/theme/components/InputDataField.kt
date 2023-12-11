package com.citibox.courier.sdk.example.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun InputDataField(
    textDefault: String? = null,
    label: String? = null,
    isMandatory: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    onValueChanged: (String) -> Unit,
) {
    var data by remember { mutableStateOf(TextFieldValue(textDefault.orEmpty())) }
    OutlinedTextField(
        modifier = Modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .fillMaxWidth(),
        label = { Text(style = MaterialTheme.typography.bodyLarge, text = label.orEmpty()) },
        keyboardOptions = keyboardOptions,
        value = data,
        onValueChange = {
            data = it
            onValueChanged(data.text)
        },
        singleLine = true,
    )
    if (isMandatory) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
            text = "* Required",
        )
    }
}
