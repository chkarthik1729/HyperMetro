package hypermetro;

import hypermetro.command.CommandHandler;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

import static hypermetro.jsonparser.JSONLaneParser.readLanesFromFile;

public class Main {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : null;
        if (fileName == null) {
            System.out.println("Error: No file specified!");
            return;
        }

        Map<String, Lane> lanes = null;
        try {
            lanes = readLanesFromFile(fileName);
        } catch (Exception e) {
            System.out.println("Error: Incorrect file!");
        }

        executeCommands(lanes);
    }

    private static void executeCommands(Map<String, Lane> lanes) {
        try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
            String command;
            while ((command = sc.nextLine()) != null) {
                try {
                    CommandHandler commandHandler = CommandHandler.getInstance(command);
                    if (commandHandler == null) return;
                    commandHandler.executeCommand(lanes, command);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}