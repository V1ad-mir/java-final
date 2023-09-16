package presenter;

import model.Toy;

import java.util.PriorityQueue;
import java.util.Random;

public class ToyQueue {
    private PriorityQueue<Toy> toyQueue;

    public ToyQueue() {
        toyQueue = new PriorityQueue<>();
    }

    public void addToy(Toy toy) {
        toyQueue.add(toy);
    }

    public int getRandomToyId() {
        Random random = new Random();
        int totalFrequency = toyQueue.stream().mapToInt(Toy::getFrequency).sum();
        int randomNumber = random.nextInt(totalFrequency) + 1;
        int cumulativeFrequency = 0;

        for (Toy toy : toyQueue) {
            cumulativeFrequency += toy.getFrequency();
            if (randomNumber <= cumulativeFrequency) {
                return toy.getId();
            }
        }

        return -1; // если коллекция пуста или что-то пошло не так
    }
}