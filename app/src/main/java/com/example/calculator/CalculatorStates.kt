package com.example.calculator

data class CalculatorStates(
    val firstNumber: String = "",
    val secondNumber: String = "",
    val operator: CalculatorOperation? = null,
)