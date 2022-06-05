package hr.nimai.spending.presentation.add_racun.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun RacunTextField(
    text: String,
    label: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    maxLines: Int = 10,
    isError: Boolean = false,
    isEnabled: Boolean = true,
) {
    Box(
        modifier = modifier
    ) {

        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            label = {
                Text(text = label)
            },
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = maxLines,
            isError = isError,
            enabled = isEnabled,
        )
    }
}