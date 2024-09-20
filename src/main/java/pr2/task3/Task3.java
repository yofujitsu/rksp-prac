package pr2.task3;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class Task3 {

    public static long checkSum(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            FileChannel fc = fis.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(2);
            long sum = 0;
            while (fc.read(byteBuffer) != -1) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    sum += byteBuffer.get();
                }
                byteBuffer.clear();
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        String filePath = "src\\main\\java\\pr2\\task3\\input.txt";
        try {
            long sum = checkSum(filePath);
            System.out.printf("Контрольная сумма файла %s: 0x%04X%n", filePath, sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}