package view;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
    }

    public int readInt() {
        int value = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки
        return value;
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }
}