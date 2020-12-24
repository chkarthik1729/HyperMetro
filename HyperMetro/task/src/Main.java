import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : null;
        if (fileName == null) {
            System.out.println("Error: No file specified!");
            return;
        }

        HashMap<String, DoublyLinkedList<String>> lanes = null;
        try {
            lanes = readLanesFromFile(fileName);
        } catch (Exception e) {
            System.out.println("Error: Incorrect file");
        }

        executeCommands(lanes);
    }

    private static HashMap<String, DoublyLinkedList<String>> readLanesFromFile(String fileName) throws IOException {
        var result = new HashMap<String, DoublyLinkedList<String>>();

        try (var reader = new BufferedReader(new FileReader(fileName))){
            var parser = new JsonParser();
            var outerObject = parser.parse(reader).getAsJsonObject();

            var lanes = outerObject.keySet();
            lanes.forEach(lane -> result.put(lane, readLane(outerObject.get(lane).getAsJsonObject())));
            return result;
        }
    }

    private static DoublyLinkedList<String> readLane(JsonObject lane) {
        var laneList = new DoublyLinkedList<String>();
        lane.entrySet().forEach(entry -> laneList.addLast(entry.getValue().getAsString()));
        return laneList;
    }

    static final String commandSyntax = "^\\/(?<command>[^\\s]+)( (?<laneName>([^\"\\s]*)|(\"[^\"]+\")))?( (?<stationName>([^\\s]*)|(\"[^\"]+\")))?$";

    private static void executeCommands(HashMap<String, DoublyLinkedList<String>> lanes) {
        try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
            Pattern pattern = Pattern.compile(commandSyntax);

            while (true) {
                Matcher matcher = pattern.matcher(sc.nextLine());
                matcher.find();
                String command = matcher.group("command");

                if ("exit".equals(command)) return;

                String laneName = matcher.group("laneName");
                laneName = laneName != null ? sanitizeString(laneName) : null;
                String stationName = matcher.group("stationName");
                stationName = stationName != null ? sanitizeString(stationName) : null;

                DoublyLinkedList<String> lane = lanes.get(laneName);
                switch (command) {
                    case "output":
                        printLane(lane);
                        break;
                    case "append":
                        lane.addLast(stationName);
                        break;
                    case "add-head":
                        lane.addFirst(stationName);
                        break;
                    case "remove":
                        lane.remove(lane.find(stationName));
                        break;
                    default:
                        System.out.println("Invalid Command");
                }
            }
        }
    }

    private static void printLane(DoublyLinkedList<String> lane) {
        assert (lane != null);
        var curr = lane.getHead();
        String prevStation, currStation, nextStation;
        while (curr != null) {
            if (curr.getPrev() == null)
                prevStation = "depot";
            else prevStation = curr.getPrev().getValue();
            currStation = curr.getValue();
            if (curr.getNext() == null)
                nextStation = "depot";
            else nextStation = curr.getNext().getValue();
            System.out.println(String.format("%s - %s - %s", prevStation, currStation, nextStation));
            curr = curr.getNext();
        }
    }

    private static String sanitizeString(String str) {
        str = str.replaceAll("\\\"", "");
        if (str.charAt(0) == '"')
            str = str.substring(1, str.length() - 1);
        return str;
    }
}