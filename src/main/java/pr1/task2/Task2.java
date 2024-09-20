package pr1.task2;

import java.util.Scanner;
import java.util.concurrent.*;

public class Task2 {
    public static int getSquare(int x) throws InterruptedException {
        int delay = ThreadLocalRandom.current().nextInt(1, 6);
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return x * x;
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите число");
                int number = scanner.nextInt();
                Future<Integer> future = executorService.submit(() -> getSquare(number));
                try {
                    int res = future.get();
                    System.out.println(res);
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("ошибка при получении результата");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("неверный ввод");
            }
        }
    }
}
