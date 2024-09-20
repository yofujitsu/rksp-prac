package pr2.task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;


public class Task2 {
    public static void main(String[] args) throws IOException {

        String fileName = "src\\main\\java\\pr2\\task2\\input.txt";
        String destinationFileName = "src\\main\\java\\pr2\\task2\\output.txt";

        createFile(fileName, 100);

        //1
        long start = System.currentTimeMillis();
        copyFileStream(fileName, destinationFileName);
        long end = System.currentTimeMillis();
        printMemoryAndTime("copyFileStream", start, end);

        //2

        start = System.currentTimeMillis();
        copyFileChannel(fileName, destinationFileName);
        end = System.currentTimeMillis();
        printMemoryAndTime("copyFileChannel", start, end);

        //3

        start = System.currentTimeMillis();
        copyApache(fileName, destinationFileName);
        end = System.currentTimeMillis();
        printMemoryAndTime("copyFileWithApacheCommonsIO", start, end);

        //4

        start = System.currentTimeMillis();
        copyFiles(fileName, destinationFileName);
        end = System.currentTimeMillis();
        printMemoryAndTime("copyFiles", start, end);

    }

    public static void printMemoryAndTime(String type, long start, long end) {
        long time = end - start;
        System.out.println("Время выполнения " + type + ": " + time + " мс");
//        System.out.println("Память использована: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " байт");
        System.out.println();
    }

    public static void createFile(String filename, long size) throws IOException {
        byte[] data = new byte[1024 * 1024];
        FileOutputStream file = new FileOutputStream(filename);
        for (int i = 0; i < size; i++) {
            file.write(data);
        }
        file.close();
    }

    public static void copyFileStream(String source, String destination) throws IOException {
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }

    public static void copyFileChannel(String source, String destination) throws IOException {
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(destination);
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
        in.close();
        out.close();
    }

    public static void copyApache(String source, String destination) throws IOException {
        FileUtils.copyFile(new File(source), new File(destination));
    }

    public static void copyFiles(String source, String destination) throws IOException {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
