package com.example.zobazedailyexpensemanager.ui.expensereport

import android.graphics.Color.HSVToColor
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zobazedailyexpensemanager.ui.model.CategoryExpenseReport
import com.example.zobazedailyexpensemanager.ui.model.DailyExpenseReport
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.bar.SimpleBarDrawer
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer
import me.bytebeats.views.charts.bar.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.bar.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseReportScreen(
    onBackClick: (() -> Unit)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expense Report") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )

            )
        },
        content = { innerPadding ->
            // Your screen content here
            ExpenseReportMainContent(innerPadding)
        }
    )
}

@Composable
fun ExpenseReportMainContent(innerPadding: PaddingValues) {
    val viewModel: ExpenseReportViewModel = hiltViewModel()
    val dailyTotals = viewModel.dailyExpenseReport.collectAsState().value
    Log.i("ergbjhsd", "ExpenseReportMainContent: ${dailyTotals}")
    val categoryExpenses = viewModel.categoryExpenseReport.collectAsState().value


    Column(modifier = Modifier.padding(innerPadding)) {
        DailyTotalBarChart(dailyTotals)
        Spacer(modifier = Modifier.height(16.dp))
        CategoryExpenseBarChart(categoryExpenses)
    }


}


@Composable
private fun DailyTotalBarChart(
    dailyTotals: List<DailyExpenseReport>
) {
    if (dailyTotals.isEmpty()) {
        Text("No data available for chart.")
        return
    }

    BarChart(
        barChartData = BarChartData(
            bars = dailyTotals.map { report ->
                BarChartData.Bar(
                    label = report.date,
                    value = report.totalAmount.toFloat(),
                    color = Color(
                        HSVToColor(
                            floatArrayOf((0..360).random().toFloat(), 0.6f, 0.9f)
                        )
                    )
                )
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        animation = simpleChartAnimation(),
        barDrawer = SimpleBarDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(
            axisLineColor = Color.Gray,
            axisLineThickness = 2.dp
        ),

        yAxisDrawer = SimpleYAxisDrawer(
            axisLineColor = Color.Gray,
            axisLineThickness = 2.dp,
            labelTextSize = 14.sp,
            labelTextColor = Color.DarkGray
        ),

        labelDrawer = SimpleLabelDrawer(
            drawLocation = SimpleLabelDrawer.DrawLocation.Outside,
            labelTextColor = Color.Black,
            labelTextSize = 10.sp
        )
    )
}

@Composable
fun CategoryExpenseBarChart(categoryTotals: List<CategoryExpenseReport>) {
    if (categoryTotals.isEmpty()) {
        Text("No data available for chart.")
        return
    }

    BarChart(
        barChartData = BarChartData(
            bars = categoryTotals.mapIndexed { index, report ->
                BarChartData.Bar(
                    label = report.category,
                    value = report.totalAmount.toFloat(),
                    color = Color(
                        HSVToColor(
                            floatArrayOf((0..360).random().toFloat(), 0.6f, 0.9f)
                        )
                    )
                )
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        animation = simpleChartAnimation(),

        // Rounded colorful bars
        barDrawer = SimpleBarDrawer(),

        // X Axis (category labels)
        xAxisDrawer = SimpleXAxisDrawer(
            axisLineColor = Color.Gray,
            axisLineThickness = 2.dp
        ),

        // Y Axis (amounts)
        yAxisDrawer = SimpleYAxisDrawer(
            axisLineColor = Color.Gray,
            axisLineThickness = 2.dp,
            labelTextSize = 14.sp,
            labelTextColor = Color.DarkGray
        ),

        // Show amounts above each bar
        labelDrawer = SimpleLabelDrawer(
            drawLocation = SimpleLabelDrawer.DrawLocation.Outside,
            labelTextColor = Color.Black,
            labelTextSize = 14.sp
        )
    )

}