package ru.kazakova_net.learnrxjava;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Операторы, с помощью которых вы можете создавать новые Observable или менять уже существующие.
 */
public class CreationOperators {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    public static void run() {
        observeRange();
        
        observeInterval();
        
        fromCallable();
    }
    
    /**
     * Оператор range выдаст последовательность чисел
     * <p>
     * Мы указываем, что начать необходимо с 10, а кол-во элементов 4
     */
    private static void observeRange() {
        // create observable
        Observable<Integer> observable = Observable.range(10, 4);
        
        // create observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(LOG_TAG, "onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "onError: " + e);
                
            }
            
            @Override
            public void onNext(Integer integer) {
                Log.d(LOG_TAG, "onNext: " + integer);
                
            }
        };
        
        // subscribe
        observable.subscribe(observer);
    }
    
    /**
     * Оператор interval выдает последовательность long чисел начиная с 0.
     * <p>
     * Мы можем указать временной интервал, через который числа будут приходить. Укажем 500 мсек.
     */
    private static void observeInterval() {
        // create observable
        Observable<Long> observable = Observable.interval(500, TimeUnit.MILLISECONDS);
        
        // create observer
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(LOG_TAG, "onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "onError: " + e);
                
            }
            
            @Override
            public void onNext(Long t) {
                Log.d(LOG_TAG, "onNext: " + t);
                
            }
        };
        
        // subscribe
        observable.subscribe(observer);
    }
    
    /**
     * Синхронный метод, который вам надо сделать асинхронным
     */
    private static int longAction(String text) {
        Log.d(LOG_TAG, "longAction");
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return Integer.parseInt(text);
    }
    
    /**
     * Получившийся Observable запустит метод longAction и вернет вам результат в onNext
     */
    private static void fromCallable() {
        Observable.fromCallable(new CallableLongAction("5"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(LOG_TAG, "onNext: " + integer);
                    }
                });
    }
    
    /**
     * Метод longAction оборачивается в {@link Callable}
     */
    private static class CallableLongAction implements Callable<Integer> {
        private final String data;

        CallableLongAction(String data) {
            this.data = data;
        }

        @Override
        public Integer call() throws Exception {
            return longAction(data);
        }
    }
}
