package com.donghanx.sets.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.donghanx.sets.R

@Composable
fun BottomSheetContentWrapper(
    onViewResultsClick: () -> Unit,
    onResetClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()

        Button(
            onClick = onViewResultsClick,
            modifier = Modifier.fillMaxWidth().height(40.dp),
        ) {
            Text(text = stringResource(id = R.string.view_results), fontSize = 16.sp)
        }

        TextButton(onClick = onResetClick, modifier = Modifier.fillMaxWidth().height(40.dp)) {
            Text(text = stringResource(id = R.string.reset), fontSize = 16.sp)
        }
    }
}
