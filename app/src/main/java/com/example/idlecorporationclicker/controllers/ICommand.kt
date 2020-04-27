package com.example.idlecorporationclicker.controllers

interface ICommand {
    fun Execute()
    fun CanExecute() : Boolean
    fun Undo()
}