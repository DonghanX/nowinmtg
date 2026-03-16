package com.donghanx.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.theme.NowInMTGTheme
import com.donghanx.model.ContrastLevel
import com.donghanx.model.DarkModeConfig
import com.donghanx.model.ThemeConfig
import com.donghanx.model.UserPreference

@Composable
fun SettingsDialog(viewModel: SettingsViewModel = hiltViewModel()) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsDialog(
        settingsUiState = settingsUiState,
        onUpdateThemeConfig = viewModel::updateThemeConfig,
        onUpdateDarkModeConfig = viewModel::updateDarkModeConfig,
        onUpdateContrastLevel = viewModel::updateContrastLevel,
    )
}

@Composable
fun SettingsDialog(
    settingsUiState: SettingsUiState,
    onUpdateThemeConfig: (ThemeConfig) -> Unit,
    onUpdateDarkModeConfig: (DarkModeConfig) -> Unit,
    onUpdateContrastLevel: (ContrastLevel) -> Unit,
) {
    Column(
        modifier =
            Modifier.fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .padding(all = 20.dp)
                .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.settings),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start),
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)

        when (settingsUiState) {
            is SettingsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is SettingsUiState.Success -> {
                SettingsPanel(
                    userPreference = settingsUiState.userPreference,
                    onUpdateThemeConfig = onUpdateThemeConfig,
                    onUpdateDarkModeConfig = onUpdateDarkModeConfig,
                    onUpdateContrastLevel = onUpdateContrastLevel,
                )
            }
        }
    }
}

@Composable
private fun SettingsPanel(
    userPreference: UserPreference,
    onUpdateThemeConfig: (ThemeConfig) -> Unit,
    onUpdateDarkModeConfig: (DarkModeConfig) -> Unit,
    onUpdateContrastLevel: (ContrastLevel) -> Unit,
) {
    Column {
        SettingsRadioGroupSection(
            title = stringResource(R.string.theme),
            options = ThemeConfig.entries,
            selectedOption = userPreference.themeConfig,
            optionLabelProvider = { toOptionLabel() },
            onOptionSelected = onUpdateThemeConfig,
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsRadioGroupSection(
            title = stringResource(R.string.dark_mode),
            options = DarkModeConfig.entries,
            selectedOption = userPreference.darkModeConfig,
            optionLabelProvider = { toOptionLabel() },
            onOptionSelected = onUpdateDarkModeConfig,
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(visible = !userPreference.themeConfig.useDynamicColor) {
            SettingsSegmentedButtonsSection(
                title = stringResource(R.string.visual_contrast),
                secondaryTitle = stringResource(R.string.visual_contrast_secondary_title),
                options = ContrastLevel.entries,
                selectedOption = userPreference.contrastLevel,
                optionLabelProvider = { toOptionLabel() },
                onOptionSelected = onUpdateContrastLevel,
            )
        }
    }
}

@Composable
private fun <T> SettingsRadioGroupSection(
    title: String,
    options: List<T>,
    selectedOption: T,
    optionLabelProvider: @Composable T.() -> String,
    onOptionSelected: (T) -> Unit,
) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(Modifier.selectableGroup()) {
            options.forEach { option ->
                SelectorRow(
                    text = option.optionLabelProvider(),
                    selected = selectedOption == option,
                    onOptionSelected = { onOptionSelected(option) },
                )
            }
        }
    }
}

@Composable
private fun <T> SettingsSegmentedButtonsSection(
    title: String,
    secondaryTitle: String,
    options: List<T>,
    selectedOption: T,
    optionLabelProvider: @Composable T.() -> String,
    onOptionSelected: (T) -> Unit,
) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = secondaryTitle, fontSize = 14.sp, modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(4.dp))

        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    shape = SegmentedButtonDefaults.itemShape(index, options.size),
                    label = { Text(text = optionLabelProvider(option)) },
                )
            }
        }
    }
}

@Composable
private fun SelectorRow(text: String, selected: Boolean, onOptionSelected: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = { onOptionSelected() },
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = null)

        Spacer(Modifier.width(8.dp))

        Text(text)
    }
}

@Composable
private fun ThemeConfig.toOptionLabel(): String {
    val resId =
        when (this) {
            ThemeConfig.DEFAULT -> R.string.default_theme
            ThemeConfig.DYNAMIC_COLOR -> R.string.dynamic_color
        }
    return stringResource(resId)
}

@Composable
private fun DarkModeConfig.toOptionLabel(): String {
    val resId =
        when (this) {
            DarkModeConfig.SYSTEM_DEFAULT -> R.string.system_default
            DarkModeConfig.LIGHT -> R.string.light
            DarkModeConfig.DARK -> R.string.dark_mode
        }
    return stringResource(resId)
}

@Composable
private fun ContrastLevel.toOptionLabel(): String {
    val resId =
        when (this) {
            ContrastLevel.LOW -> R.string.low
            ContrastLevel.MEDIUM -> R.string.medium
            ContrastLevel.HIGH -> R.string.high
        }
    return stringResource(resId)
}

@Preview
@Composable
private fun SettingsDialogLoadingPreview() {
    NowInMTGTheme {
        SettingsDialog(
            settingsUiState = SettingsUiState.Loading,
            onUpdateThemeConfig = {},
            onUpdateDarkModeConfig = {},
            onUpdateContrastLevel = {},
        )
    }
}

@Preview
@Composable
private fun SettingsDialogSuccessWithDynamicColorPreview(
    @PreviewParameter(SettingsDialogPreviewParameterProvider::class) userPreference: UserPreference
) {
    NowInMTGTheme(
        useDynamicColor = userPreference.themeConfig.useDynamicColor,
        useDarkMode = userPreference.darkModeConfig.useDarkMode(true),
    ) {
        Surface {
            SettingsDialog(
                settingsUiState = SettingsUiState.Success(userPreference),
                onUpdateThemeConfig = {},
                onUpdateDarkModeConfig = {},
                onUpdateContrastLevel = {},
            )
        }

    }
}

class SettingsDialogPreviewParameterProvider : PreviewParameterProvider<UserPreference> {
    override val values: Sequence<UserPreference>
        get() =
            sequenceOf(
                UserPreference(
                    themeConfig = ThemeConfig.DYNAMIC_COLOR,
                    darkModeConfig = DarkModeConfig.LIGHT,
                ),
                UserPreference(
                    themeConfig = ThemeConfig.DEFAULT,
                    darkModeConfig = DarkModeConfig.LIGHT,
                ),
                UserPreference(
                    themeConfig = ThemeConfig.DYNAMIC_COLOR,
                    darkModeConfig = DarkModeConfig.DARK,
                ),
                UserPreference(
                    themeConfig = ThemeConfig.DEFAULT,
                    darkModeConfig = DarkModeConfig.DARK,
                ),
            )
}
