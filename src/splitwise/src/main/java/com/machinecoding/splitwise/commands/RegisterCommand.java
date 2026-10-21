package com.machinecoding.splitwise.commands;

public class RegisterCommand implements Command {
    @Override
    public boolean matches(String input) {
        String words[] = input.split(" ");
        if(words[0].equalsIgnoreCase("register"))
            return true;

        return false;
    }

    @Override
    public void execute(String input) {
        System.out.println("Registering...");
    }
}
