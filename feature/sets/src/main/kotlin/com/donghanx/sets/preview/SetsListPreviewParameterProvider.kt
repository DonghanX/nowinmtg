package com.donghanx.sets.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.donghanx.mock.MockUtils
import com.donghanx.model.SetInfo
import java.time.LocalDate

class SetsListPreviewParameterProvider : PreviewParameterProvider<Map<Int, List<SetInfo>>> {
    override val values: Sequence<Map<Int, List<SetInfo>>>
        get() =
            sequenceOf(
                listOf(MockUtils.soiExpansion, MockUtils.xlnExpansion).groupBy {
                    LocalDate.parse(it.releasedAt).year
                },
                emptyMap(),
            )
}
