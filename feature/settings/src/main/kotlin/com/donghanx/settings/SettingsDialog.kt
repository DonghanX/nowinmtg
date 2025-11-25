package com.donghanx.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.donghanx.design.theme.NowInMTGTheme

@Composable
fun SettingsDialog() {
    Column(
        modifier =
            Modifier.fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                .padding(all = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start),
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)

        SettingsSection(
            title = stringResource(R.string.theme),
            options = stringArrayResource(R.array.color_theme_options),
            onOptionSelected = {},
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingsSection(
            title = stringResource(R.string.dark_mode),
            options = stringArrayResource(R.array.dark_mode_options),
            onOptionSelected = {},
        )
    }
}

@Composable
private fun ColumnScope.SettingsSection(
    title: String,
    options: Array<String>,
    onOptionSelected: (String) -> Unit,
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options.first()) }

    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.align(Alignment.Start),
    )

    Spacer(modifier = Modifier.height(12.dp))

    Column(Modifier.selectableGroup()) {
        options.forEach { option ->
            SelectorRow(
                text = option,
                selected = selectedOption == option,
                onOptionSelected = onOptionSelected,
            )
        }
    }
}

@Composable
private fun SelectorRow(text: String, selected: Boolean, onOptionSelected: (String) -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = { onOptionSelected(text) },
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = null)

        Spacer(Modifier.width(8.dp))

        Text(text)
    }
}

@Preview
@Composable
private fun SettingsDialogPreview() {
    NowInMTGTheme { SettingsDialog() }
}
