package ru.kazakova_net.learnrxjava;

import android.util.Log;

import rx.Observable;
import rx.Observer;

/**
 * Эти операторы позволяют фильтровать данные
 */
public class FilteringOperators {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    public static void run() {
//        take();

//        skip();

//        distinct();
        
        filter();
    }
    
    /**
     * Оператор take возьмет только указанное количество первых элементов из переданной ему
     * последовательности и сформирует из них новую последовательность. Возьмем первые три
     */
    private static void take() {
        // create observable
        Observable<Integer> observable = Observable
                .from(new Integer[]{5, 6, 7, 8, 9})
                .take(3);
        
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
     * Оператор skip пропустит первые элементы. Пропустим первые 2
     */
    private static void skip() {
        // create observable
        Observable<Integer> observable = Observable
                .from(new Integer[]{5, 6, 7, 8, 9})
                .skip(2);
        
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
            public void onNext(Integer s) {
                Log.d(LOG_TAG, "onNext: " + s);
            }
        };
        
        // subscribe
        observable.subscribe(observer);
    }
    
    /**
     * Оператор distinct отсеет дубликаты
     */
    private static void distinct() {
        // create observable
        Observable<Integer> observable = Observable
                .from(new Integer[]{5, 9, 7, 5, 8, 6, 7, 8, 9})
                .distinct();
        
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
            public void onNext(Integer s) {
                Log.d(LOG_TAG, "onNext: " + s);
            }
        };
        
        // subscribe
        observable.subscribe(observer);
    }
    
    /**
     * Оператор filter может отсеять только нужные элементы.
     * Для этого необходимо создать функцию, в которой будет описан алгоритм фильтрации.
     * Например, оставим только строки содержащие 5.
     */
    private static void filter() {
        Observable.from(new String[]{"15", "27", "34", "46", "52", "63"})
                .filter(number -> number.contains("5"))
                .subscribe(number -> Log.d(LOG_TAG, number));
    }
}
