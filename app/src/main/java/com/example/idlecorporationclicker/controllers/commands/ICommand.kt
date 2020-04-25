package com.example.idlecorporationclicker.controllers.commands

interface ICommand {
    fun Execute()
    fun CanExecute() : Boolean
    fun Undo()
}