package pr1.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Task1 {

    public static List<Integer> generateArray() {
        List<Integer> array = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            array.add(random.nextInt(100));
        }
        return array;
    }

    public static int sumArray(List<Integer> array) throws InterruptedException{
        int sum = 0;
        if(array == null){
            throw new InterruptedException("Список пуст");
        }
        for (int i : array) {
            Thread.sleep(1);
            sum += i;
        }
        return sum;
    }

    public static int sumArrayThread(List<Integer> array) throws InterruptedException, ExecutionException {
        int sum = 0;
        if(array == null){
            throw new InterruptedException("Список пуст");
        }
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Callable<Integer>> tasks = new ArrayList<>();
        int batchSize = array.size() / threads;
        for (int i = 0; i < threads; i++) {
            int start = i * batchSize;
            int end;
            if (i == threads - 1) {
                end = array.size();
            } else {
                end = (i + 1) * batchSize;
            }
            tasks.add(() -> sumArray(array.subList(start, end)));
        }
        List<Future<Integer>> futures = executor.invokeAll(tasks);
        for (Future<Integer> future : futures) {
            Thread.sleep(1);
            sum += future.get();
        }
        return sum;
    }

    static class SumTask extends RecursiveTask<Long> {
        private final List<Integer> array;
        private final int start;
        private final int end;

        public SumTask(List<Integer> array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= 1000) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array.get(i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return sum;
            } else {
                int middle = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, middle);
                SumTask rightTask = new SumTask(array, middle, end);

                leftTask.fork();
                long rightResult = rightTask.compute();
                long leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }
    }

    public static int sumArrayForkJoin(List<Integer> array) throws InterruptedException, ExecutionException {
        int sum = 0;
        if(array == null){
            throw new InterruptedException("Список пуст");
        }
        int threads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threads);
        SumTask task = new SumTask(array, 0, array.size());
        sum = Math.toIntExact(forkJoinPool.invoke(task));
        return sum;
    }



    public static void main(String[] args) {
        List<Integer> array = generateArray();
        try {
            long startTime = System.currentTimeMillis();
            long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            int sum = sumArrayForkJoin(array);
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long memoryUsed1 = afterMemory - beforeMemory;
            System.out.println("Время выполнения sumArrayForkJoin: " + time + " мс");
            System.out.println("Сумма: " + sum);
            System.out.println("Память использована: " + memoryUsed1);
            startTime = System.currentTimeMillis();
            beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            sum = sumArrayThread(array);
            afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            memoryUsed1 = afterMemory - beforeMemory;
            endTime = System.currentTimeMillis();
            time = endTime - startTime;
            System.out.println("Время выполнения sumArrayThread: " + time + " мс");
            System.out.println("Сумма: " + sum);
            System.out.println("Память использована: " + memoryUsed1);
            startTime = System.currentTimeMillis();
            beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            sum = sumArray(array);
            afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            memoryUsed1 = afterMemory - beforeMemory;
            endTime = System.currentTimeMillis();
            time = endTime - startTime;
            System.out.println("Время выполнения sumArray: " + time + " мс");
            System.out.println("Сумма: " + sum);
            System.out.println("Память использована: " + memoryUsed1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
