package pr2.task4;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task4 {

    public static Map<Path, List<String>> filesMap = new HashMap<>();

    public static List<String> readFile(Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void detectFileChanges(Path filePath) throws IOException {
        List<String> newLines = readFile(filePath);
        List<String> oldLines = filesMap.get(filePath);
        if (oldLines == null) {
            oldLines = new ArrayList<>();
        }
        for (String line : newLines) {
            if (!oldLines.contains(line)) {
                System.out.println("Добавлено: " + line);
            }
        }
        for (String line : oldLines) {
            if (!newLines.contains(line)) {
                System.out.println("Удалено: " + line);
            }
        }
        filesMap.put(filePath, newLines);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("src\\main\\java\\pr2\\task4");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        while (true) {
            WatchKey watchKey = watchService.take();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path filePath = (Path) event.context();
                    System.out.println("Создал файл: " + filePath);
                    filesMap.put(filePath, readFile(path.resolve(filePath)));
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Path filePath = (Path) event.context();
                    System.out.println("Файл изменен: " + filePath);
                    detectFileChanges(path.resolve(filePath));
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    Path filePath = (Path) event.context();
                    System.out.println("Файл удален: " + filePath);
                    filesMap.remove(filePath);
                }
            }
            watchKey.reset();
        }
    }
}