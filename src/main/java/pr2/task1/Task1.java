package pr2.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        String filename = "src/main/java/pr2/task1/input.txt";
        Path path = Paths.get(filename);
        List<String> lines = List.of("dota2", "deadlock");
        try {
            Files.write(path, lines);
            System.out.println("Файл создан: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + filename);
        }
        try {
            List<String> linesFromFile = Files.readAllLines(path);
            System.out.println("Содержимое файла " + filename);
            for (String line : linesFromFile) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + filename);
        }
    }
}
