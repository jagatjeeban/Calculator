package com.example.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.calculator.ui.theme.LightGrey
import com.example.calculator.ui.theme.MediumGrey
import com.example.calculator.ui.theme.Orange
import kotlinx.coroutines.delay

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    state: CalculatorStates,
    buttonSpacing: Dp = 8.dp,
    onClickButton: (CalculatorAction) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            ResultText(state = state)
            ButtonsRow(
                symbol = listOf("AC", "Del", "/"),
                buttonSpacing = buttonSpacing,
                onClick = {
                    when (it) {
                        "AC" -> onClickButton(CalculatorAction.ClearAll)
                        "Del" -> onClickButton(CalculatorAction.Delete)
                        "/" -> onClickButton(CalculatorAction.Operation(CalculatorOperation.Divide))
                    }
                }
            )
            ButtonsRow(
                symbol = listOf("7", "8", "9", "x"),
                buttonSpacing = buttonSpacing,
                onClick = {
                    when (it) {
                        "7" -> onClickButton(CalculatorAction.Number(7))
                        "8" -> onClickButton(CalculatorAction.Number(8))
                        "9" -> onClickButton(CalculatorAction.Number(9))
                        "x" -> onClickButton(CalculatorAction.Operation(CalculatorOperation.Multiply))
                    }
                }
            )
            ButtonsRow(
                symbol = listOf("4", "5", "6", "-"),
                buttonSpacing = buttonSpacing,
                onClick = {
                    when (it) {
                        "4" -> onClickButton(CalculatorAction.Number(4))
                        "5" -> onClickButton(CalculatorAction.Number(5))
                        "6" -> onClickButton(CalculatorAction.Number(6))
                        "-" -> onClickButton(CalculatorAction.Operation(CalculatorOperation.Subtract))
                    }
                }
            )
            ButtonsRow(
                symbol = listOf("1", "2", "3", "+"),
                buttonSpacing = buttonSpacing,
                onClick = {
                    when (it) {
                        "1" -> onClickButton(CalculatorAction.Number(1))
                        "2" -> onClickButton(CalculatorAction.Number(2))
                        "3" -> onClickButton(CalculatorAction.Number(3))
                        "+" -> onClickButton(CalculatorAction.Operation(CalculatorOperation.Add))
                    }
                }
            )
            ButtonsRow(
                symbol = listOf("0", ".", "="),
                buttonSpacing = buttonSpacing,
                modifier = Modifier.padding(bottom = 30.dp),
                onClick = {
                    when (it) {
                        "0" -> onClickButton(CalculatorAction.Number(0))
                        "." -> onClickButton(CalculatorAction.Decimal)
                        "=" -> onClickButton(CalculatorAction.Calculate)
                    }
                }
            )
        }
    }
}

@Composable
fun ResultText(state: CalculatorStates) {
    val scrollState = rememberScrollState()
    val finalText = state.firstNumber + (state.operator?.symbol ?: "") + state.secondNumber
    LaunchedEffect(state.firstNumber, state.operator, state.secondNumber) {
        delay(100)
        scrollState.animateScrollTo(scrollState.maxValue)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(vertical = 32.dp, horizontal = 15.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Text(
            text = finalText,
            textAlign = TextAlign.End,
            color = Color.White,
            fontSize = if(finalText.length <= 10) 70.sp else if(finalText.length <= 15) 50.sp else 30.sp,
            fontWeight = FontWeight.Light,
            softWrap = false,
            maxLines = 1
        )
    }
}

@Composable
fun ButtonsRow(
    modifier: Modifier = Modifier,
    symbol: List<String>,
    buttonSpacing: Dp,
    onClick: (String) -> Unit
) {
    val itemCount = symbol.size
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val totalSpacing = buttonSpacing * (itemCount -1 )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        contentPadding = PaddingValues(horizontal = buttonSpacing),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        items(symbol.size){ index ->
            val buttonWidth = when{
                itemCount == 3 && index == 0 ->
                    (screenWidth - (totalSpacing + (2 * buttonSpacing))) / 2
                else ->
                    (screenWidth - (totalSpacing + (2 * buttonSpacing))) / 4
            }
            CalculatorButton(
                text = symbol[index],
                modifier = Modifier
                    .width(buttonWidth)
                    .height(93.dp)
                    .background(if(symbol[index] == "AC" || symbol[index] == "Del") LightGrey else if(symbol[index] == "/" || symbol[index] == "=" || index == 3) Orange else MediumGrey)
                    .padding(5.dp),
                onClick = { onClick(symbol[index]) }
            )
        }
    }
}