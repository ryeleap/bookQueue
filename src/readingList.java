import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class readingList {
    private Deque<String> bookQueue;
    private static final String DATA_FILE = "books.dat";

    public readingList() {
        loadQueue();
    }

    private void saveQueue() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(bookQueue);
        } catch (IOException e) {
            System.out.println("Error!!!");
        }
    }

    private void loadQueue() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                bookQueue = (Deque<String>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Load Error!!!");
                bookQueue = new ArrayDeque<>();
            }
        } else {
            bookQueue = new ArrayDeque<>();
        }
    }

    public void addBook(String title) {
        bookQueue.addLast(title);
        saveQueue();
    }

    public void removeBook() {
        if (!bookQueue.isEmpty()) {
            bookQueue.removeFirst();
            saveQueue();
        } else {
            System.out.println("No books to remove.");
        }
    }

    public void displayQueue() {
        if (bookQueue.isEmpty()) {
            System.out.println("No books in the queue.");
        } else {
            System.out.println("Books in queue:");
            for (String title : bookQueue) {
                System.out.println(title);
            }
        }
    }

    public static void main(String[] args) {
        readingList manager = new readingList();
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Enter command (add, remove, list, quit):");
            input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "add":
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    manager.addBook(title);
                    break;
                case "remove":
                    manager.removeBook();
                    break;
                case "list":
                    manager.displayQueue();
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}
