package com.machinecoding.splitwise;

import com.machinecoding.splitwise.commands.Command;
import com.machinecoding.splitwise.commands.CommandExecutor;
import com.machinecoding.splitwise.commands.RegisterCommand;
import com.machinecoding.splitwise.commands.SettleUpCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {

    @Autowired
    private CommandExecutor commandExecutor;

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your command here: ");
        String input = sc.nextLine();
        commandExecutor.executeCommand(input);
    }
}
