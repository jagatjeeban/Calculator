package com.example.calculator

sealed class CalculatorOperation(val symbol: String) {
    data object Add: CalculatorOperation("+")
    data object Subtract: CalculatorOperation("-")
    data object Divide: CalculatorOperation("/")
    data object Multiply: CalculatorOperation("x")
}