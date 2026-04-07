package com.kmpxmachinelearning.salary.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kmpxmachinelearning.salary.domain.entity.PointEntity
import com.kmpxmachinelearning.salary.presentation.component.HrPointEntity
import com.kmpxmachinelearning.salary.presentation.component.SalaryChart
import com.kmpxmachinelearning.salary.presentation.component.SalaryChartLegend
import com.kmpxmachinelearning.salary.presentation.component.SalaryHeader
import com.kmpxmachinelearning.salary.presentation.viewmodel.SalaryViewModel
import com.kmpxmachinelearning.shared.component.BgApp
import com.kmpxmachinelearning.shared.component.CustomOutlinedTextField
import com.kmpxmachinelearning.shared.component.button.primary_button.PrimaryButton
import com.kmpxmachinelearning.shared.component.card.PrimaryCard
import com.kmpxmachinelearning.shared.component.chart.ChartContainer
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.emptyList

@Composable
fun SalaryScreen() {
    val viewModel = koinViewModel<SalaryViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold { padding ->
        BgApp(
            padding = padding,
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp)
            ) {
                PrimaryCard {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        CustomOutlinedTextField(
                            label = "HR Salary Predictor",
                            value = state.level,
                            onValueChange = viewModel::onLevelChange,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        PrimaryButton(
                            text = "Predict Salary",
                            onClick = viewModel::predict
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        if (state.salaryEntity?.salaryFormatted?.isNotEmpty() == true) {
                            SalaryHeader(
                                salary = state.salaryEntity?.salaryFormatted ?: "-",
                                category = state.salaryEntity?.category ?: "-"
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            ChartContainer {
                                SalaryChart(
                                    curve = state.salaryEntity?.curve?.map {
                                        HrPointEntity(x = it.x.toFloat(), y = it.y.toFloat())
                                    }?.toList() ?: emptyList(),
                                    real = state.salaryEntity?.realData?.map {
                                        HrPointEntity(x = it.x.toFloat(), y = it.y.toFloat())
                                    }?.toList() ?: emptyList(),
                                    user = HrPointEntity(
                                        x = viewModel.touchedSpot?.x?.toFloat() ?: 0f,
                                        y = viewModel.touchedSpot?.y?.toFloat() ?: 0f,
                                    ),
                                    onTouch = {
                                        viewModel.updateTouchedSpot(
                                            PointEntity(
                                                it?.x?.toDouble() ?: 0.0,
                                                it?.y?.toDouble() ?: 0.0,
                                            )
                                        )
                                    },
                                    formatUSD = viewModel::formatUSD,
                                )
                            }
                            SalaryChartLegend()
                        }
                    }
                }
            }
        }

    }
}