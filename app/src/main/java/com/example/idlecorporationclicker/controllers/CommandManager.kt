package com.example.idlecorporationclicker.controllers

import java.util.*

class CommandManager {
    private val commands : Stack<ICommand> = Stack<ICommand>()


    fun Invoke(command : ICommand) {
        if(command.CanExecute()) {
            commands.push(command)
            command.Execute()
        }
    }

    fun Undo() {
        while(!commands.isEmpty()) {
            var command = commands.pop()
            command.Undo()
        }
    }
}