package com.example.idlecorporationclicker.commands

interface ICommand {
    fun Execute()
    fun CanExecute() : Boolean
    fun Undo()
}