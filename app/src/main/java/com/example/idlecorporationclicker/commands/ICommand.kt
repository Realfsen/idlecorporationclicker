package com.example.idlecorporationclicker.model

interface ICommand {
    fun Execute()
    fun CanExecute()
    fun Undo()
}