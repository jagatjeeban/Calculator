package com.example.calculator

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorStates())
        private set

    //function to handle the button clicks
    fun onButtonClick(action: CalculatorAction){
        when(action){
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.ClearAll -> state = CalculatorStates()
            is CalculatorAction.Delete -> deleteAction()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> calculateResult()
        }
    }

    //function to calculate the result
    private fun calculateResult() {
        val firstNumber = state.firstNumber.toDoubleOrNull()
        val secondNumber = state.secondNumber.toDoubleOrNull()
        if(firstNumber != null && secondNumber != null){
            val result = when(state.operator){
                is CalculatorOperation.Add -> firstNumber + secondNumber
                is CalculatorOperation.Subtract -> firstNumber - secondNumber
                is CalculatorOperation.Multiply -> firstNumber * secondNumber
                is CalculatorOperation.Divide -> firstNumber / secondNumber
                null -> return
            }
            state = state.copy(
                firstNumber = result.toString().take(15),
                secondNumber = "",
                operator = null
            )
        }
    }

    //function to enter the operation
    private fun enterOperation(operation: CalculatorOperation) {
        if(state.firstNumber.isNotBlank()){
            state = state.copy(operator = operation)
        }
    }

    //function to delete the last number
    private fun deleteAction() {
        when{
            state.secondNumber.isNotBlank() -> state = state.copy(
                secondNumber = state.secondNumber.dropLast(1)
            )
            state.operator != null -> state = state.copy(
                operator = null
            )
            state.firstNumber.isNotBlank() -> state = state.copy(
                firstNumber = state.firstNumber.dropLast(1)
            )
        }
    }

    //function to enter the decimal
    private fun enterDecimal() {
        if(state.operator == null && state.firstNumber.isNotBlank() && !state.firstNumber.contains(".")){
            state = state.copy(firstNumber = state.firstNumber + ".")
        }
        else if(state.operator != null && state.secondNumber.isNotBlank() && !state.secondNumber.contains(".")){
            state = state.copy(secondNumber = state.secondNumber + ".")
        }
    }

    //function to enter the number
    private fun enterNumber(number: Int) {
        state = if(state.operator == null){
            state.copy(firstNumber = state.firstNumber + number)
        } else {
            state.copy(secondNumber = state.secondNumber + number)
        }
    }
}