package presenter;

import model.Toy;
import view.ConsoleUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToyPresenter {
    private List<Toy> toyList;
    private ConsoleUI console;
    private int nextId = 1;

    public ToyPresenter() {
        toyList = new ArrayList<>();
        console = new ConsoleUI();
    }

    public void start() {
        int choice;

        do {
            console.println("Меню:");
            console.println("1. Добавление игрушки");
            console.println("2. Розыгрыш");
            console.println("3. Просмотр списка игрушек");
            console.println("4. Просмотр сохраненного файла");
            console.println("5. Очистить список игрушек");
            console.println("0. Выход");
            console.print("Выберите пункт меню: ");
            choice = console.readInt();

            switch (choice) {
                case 1:
                    addToyManually();
                    break;
                case 2:
                    playLottery();
                    break;
                case 3:
                    viewToyList();
                    break;
                case 4:
                    viewSavedFile();
                    break;
                case 5:
                    clearToyList();
                    break;
                case 0:
                    console.println("Выход из программы");
                    break;
                default:
                    console.println("Неверный выбор. Попробуйте еще раз.");
                    break;
            }
        } while (choice != 0);
    }

    public void addToy(Toy toy) {
        toyList.add(toy);
    }

    private void clearToyList() {
        toyList.clear();
        console.println("Список игрушек очищен.");
    }
    private void viewToyList() {
        if (toyList.isEmpty()) {
            console.println("Список игрушек пуст!");
        } else {
            console.println("Список игрушек:");
            for (Toy toy : toyList) {
                console.println("ID: " + toy.getId() + ", Название: " + toy.getName() + ", Частота: " + toy.getFrequency());
            }
        }
    }

    private void viewSavedFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("result.txt"))) {
            String line;
            console.println("Содержимое сохраненного файла:");
            while ((line = reader.readLine()) != null) {
                console.println(line);
            }
        } catch (IOException e) {
            console.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public void addToyManually(int id, String name, int frequency) {
        int totalFrequency = 0;
        for (Toy toy : toyList) {
            totalFrequency += toy.getFrequency();
        }

        if (totalFrequency + frequency > 100) {
            console.println("Ошибка: общая частота выпадения игрушек превышает 100");
            toyList.clear(); // Очищаем список игрушек
            console.println("Список игрушек очищен. Добавьте игрушки заново.");
            return;
        }

        Toy toy = new Toy(id, name, frequency);
        toyList.add(toy);

        if (totalFrequency + frequency <= 100) {
            if (totalFrequency + frequency == 100) {
                console.println("Игрушка успешно добавлена! Общая частота достигла 100.");
            } else {
                console.println("Игрушка успешно добавлена!");
            }
        }
    }

    public int getRandomToyId() {
        if (toyList.isEmpty()) {
            return -1;
        }

        int totalFrequency = 0;
        for (Toy toy : toyList) {
            totalFrequency += toy.getFrequency();
        }

        Random random = new Random();
        int randomNumber = random.nextInt(totalFrequency) + 1;
        int cumulativeFrequency = 0;

        for (Toy toy : toyList) {
            cumulativeFrequency += toy.getFrequency();
            if (randomNumber <= cumulativeFrequency) {
                return toy.getId();
            }
        }

        return -1;
    }

    private void addToyManually() {
        int id = nextId;
        nextId++;
        console.println("Сгенерирован ID игрушки: " + id);

        console.print("Введите название игрушки: ");
        String name = console.readLine();

        int frequency;
        while (true) {
            console.print("Введите частоту выпадения игрушки: ");
            try {
                frequency = Integer.parseInt(console.readLine());
                break;
            } catch (NumberFormatException e) {
                console.println("Ошибка: введите числовое значение для частоты выпадения игрушки.");
            }
        }

        addToyManually(id, name, frequency);
    }

    private void playLottery() {
        if (toyList.isEmpty()) {
            console.println("Список игрушек для розыгрыша пуст!!!! Добавьте игрушку в список");
            return;
        }

        try (FileWriter writer = new FileWriter("result.txt")) {
            for (int i = 0; i < 10; i++) {
                int toyId = getRandomToyId();
                writer.write(String.valueOf(toyId));
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        console.println("Розыгрыш завершен. Результаты записаны в файл result.txt");
    }
}