package pr3.task4;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Task4 {
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

    public static class FileGenerator {
        public Observable<File> generateFile() {
            return Observable.
                    fromCallable(() -> {
                        try {
                            String[] fileTypes = {"xml", "json", "txt"};
                            String fileType = fileTypes[new java.util.Random().nextInt(fileTypes.length)];
                            int fileSize = new java.util.Random().nextInt(10);
                            Thread.sleep(1000);
                            return new File(fileType, fileSize);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }).repeat()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation());
        }
    }

    public static class FileQueue {
        private final int capacity;
        private final Observable<File> files;

        public FileQueue(int capacity) {
            this.capacity = capacity;
            this.files = new FileGenerator().generateFile().replay(capacity).autoConnect();
        }

        public Observable<File> getFiles() {
            return files;
        }
    }

    public static class FileProcessor {
        private final String supportedFileType;

        public FileProcessor(String supportedFileType) {
            this.supportedFileType = supportedFileType;
        }

        public Completable processFile(Observable<File> files) {
            return files
                    .filter(file -> file.getFileType().equals(supportedFileType))
                    .flatMapCompletable(file -> {
                        long processingTime = file.getFileSize() * 7;
                        return Completable.fromAction(() -> {
                                    Thread.sleep(processingTime);
                                    System.out.println(String.format("Processing file %s", file));
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.computation())
                                .onErrorComplete();
                    });
        }
    }

    public static void main(String[] args) {
        int capacity = 5;
        FileQueue fileQueue = new FileQueue(capacity);
        String[] supportedFileTypes = {"xml", "json", "txt"};
        for(String supportedFileType : supportedFileTypes) {
            new FileProcessor(supportedFileType).processFile(fileQueue.getFiles())
                    .subscribe( () -> {}
                    , Throwable::printStackTrace);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
