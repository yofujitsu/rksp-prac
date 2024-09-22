package pr3.task1;
import java.util.*;
import io.reactivex.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class Task1 {
    static class Temp extends Observable<Integer> {

        private final PublishSubject<Integer> subject = PublishSubject.create();

        @Override
        protected void subscribeActual(Observer<? super Integer> observer) {
            subject.subscribe(observer);
        }

        public void start() {
            new Thread(() -> {
                while (true) {
                    int temp = new Random().nextInt(15, 31);
                    subject.onNext(temp);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class CO2 extends Observable<Integer> {

        private final PublishSubject<Integer> subject = PublishSubject.create();

        @Override
        protected void subscribeActual(Observer<? super Integer> observer) {
            subject.subscribe(observer);
        }

        public void start() {
            new Thread(() -> {
                while (true) {
                    int co2 = new Random().nextInt(30, 101);
                    subject.onNext(co2);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class Alarm implements Observer<Integer> {

        private final int co2_max = 70;
        private final int temp_max = 25;

        private int temperature = 0;
        private int co2 = 0;

        @Override
        public void onSubscribe(Disposable disposable) {
            System.out.println(disposable.hashCode() + " has subscribed");
        }

        @Override
        public void onNext(Integer value) {
            System.out.println("Next value from Observable= " + value);
            if(value <= 30) {
                temperature = value;
            } else {
                co2 = value;
            }
            check();
        }

        private void check() {
            if(temperature > temp_max && co2 > co2_max) {
                System.out.println("Alarm!!!");
            } else if(co2 >= co2_max) {
                System.out.println("CO2 level is high");
            } else if (temperature >= temp_max){
                System.out.println("Temperature is high");
            }

        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println("Observable completed");
        }
    }

    public static void main(String[] args) {
        Temp temp = new Temp();
        CO2 co2 = new CO2();
        Alarm alarm = new Alarm();
        temp.subscribe(alarm);
        co2.subscribe(alarm);
        temp.start();
        co2.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
