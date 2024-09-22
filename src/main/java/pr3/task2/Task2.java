package pr3.task2;

import io.reactivex.Observable;

import java.util.Random;

public class Task2 {

    public static void main(String[] args) {
        //2.1.1
        Observable<Integer> observable = Observable
                .range(0, 1000)
                .map(i -> i * i);
        observable.subscribe(System.out::println);
        System.out.println("---------------------------");

        //2.2.1
        Observable<Integer> integers = Observable
                .range(0, 10)
                .map(i -> new Random().nextInt(0, 1000));

        Observable<String> strings = Observable
                .range(0, 10)
                .map(i -> "String" + i + ".");

        Observable<String> combo = Observable
                .zip(strings, integers, (s, i) -> s + i);

        for(Object s : combo.blockingIterable()){
            System.out.println(s);
        }
        System.out.println("---------------------------");

        //2.3.1
        Observable<Integer> rndm = Observable
                .range(0, 10)
                .map(i -> new Random().nextInt(0, 1000));

        rndm.subscribe(System.out::println);
        System.out.println("---------------------------");
        rndm.skip(3).subscribe(System.out::println);
    }
}
