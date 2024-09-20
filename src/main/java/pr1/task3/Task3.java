package pr1.task3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task3 {
    public static class File {

        private final String fileType;
        private final int fileSize;
        public File(String fileType, int fileSize) {
            this.fileType = fileType;
            this.fileSize = fileSize;
        }
        public String getFileType() {
            return fileType;
        }
        public int getFileSize() {
            return fileSize;
        }
    }

    static class FileGenerator implements Runnable {
        private BlockingQueue<File> queue;
        public FileGenerator(BlockingQueue<File> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            Random random = new Random();
            String[] fileTypes = {"xml", "json", "xls"};
            while (true) {
                try {
                    Thread.sleep(random.nextInt(1000));
                    queue.put(new File(fileTypes[random.nextInt(3)], random.nextInt(1000)));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    static class FileProcessor implements Runnable {
        private final BlockingQueue<File> queue;
        private final String allowedFileType;
        public FileProcessor(BlockingQueue<File> queue, String allowedFileType) {
            this.queue = queue;
            this.allowedFileType = allowedFileType;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    File file = queue.take();
                    if (file.getFileType().equals(allowedFileType)) {
                        long processingTime = file.getFileSize() * 7L;
                        Thread.sleep(processingTime);
                        System.out.println("Обработан файл типа " +
                                        file.getFileType() +
                                        " с размером " + file.getFileSize() + ". Время обработки: " + processingTime + " мс.");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<>(5);
        Thread generatorThread = new Thread(new FileGenerator(queue));
        Thread jsonProcessorThread = new Thread(new FileProcessor(queue,
                "json"));
        Thread xmlProcessorThread = new Thread(new FileProcessor(queue, "xml"));
        Thread xlsProcessorThread = new Thread(new FileProcessor(queue, "xls"));

        generatorThread.start();
        jsonProcessorThread.start();
        xmlProcessorThread.start();
        xlsProcessorThread.start();
    }
}
