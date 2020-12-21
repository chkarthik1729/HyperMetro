import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : null;

        if (fileName == null) {
            System.out.println("Error: No file specified!");
            return;
        }

        File stationsFile = new File(fileName);

        try (Scanner fileScanner = new Scanner(stationsFile)) {
            SinglyLinkedList<String> stations = readIntoALinkedList(fileScanner);
            if (!stations.isEmpty()) {
                printStations(stations);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File doesn't exist!");
        }
    }

    private static SinglyLinkedList<String> readIntoALinkedList(Scanner sc) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) list.addLast(line);
        }
        return list;
    }

    private static void printStations(SinglyLinkedList<String> stations) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("depot");
        var curr = stations.getHead();
        if (curr != null) buffer.append(" - ").append(curr.getData());
        if (curr != null && curr.getNext() != null) buffer.append(" - ").append(curr.getNext().getData()).append("\n");

        while (curr != null) {
            buffer.append(curr.getData());
            if (curr.getNext() != null)
                buffer.append(" - ").append(curr.getNext().getData());
            if (curr.getNext() != null && curr.getNext().getNext() != null)
                buffer.append(" - ").append(curr.getNext().getNext().getData()).append("\n");
            curr = curr.getNext();
        }

        buffer.append(" - depot");
        System.out.println(buffer);
    }
}
