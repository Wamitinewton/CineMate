package com.newton.shared_ui.sharedComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout
import kotlin.math.max

@Composable
fun FlowRow(
    maxItemsInEachRow: Int,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    content: @Composable () -> Unit
) {
    Layout(
        content = content
    ) { measurables, constraints ->
        val rowWidths = IntArray(measurables.size / maxItemsInEachRow + 1){ 0 }
        val rowHeights = IntArray(rowWidths.size) { 0 }
        val placeables = measurables.mapIndexed { index, measurable ->
            val rowIndex = index / maxItemsInEachRow
            val placeable = measurable.measure(constraints)
            rowWidths[rowIndex] += placeable.width
            rowHeights[rowIndex] = max(rowHeights[rowIndex], placeable.height)
            placeable
        }

        val width = constraints.maxWidth
        val height = rowHeights.sum()

        layout(width, height) {
            var y = 0
            var x = 0
            var rowIndex = 0

            placeables.forEachIndexed { index, placeable ->
                if (index % maxItemsInEachRow == 0) {

                    x = 0
                    if (index > 0) {
                        y += rowHeights[rowIndex - 1] + verticalArrangement.spacing.roundToPx()
                    }
                    rowIndex++
                }

                placeable.placeRelative(x = x, y = y)
                x+= placeable.width + horizontalArrangement.spacing.roundToPx()
            }
        }
    }
}