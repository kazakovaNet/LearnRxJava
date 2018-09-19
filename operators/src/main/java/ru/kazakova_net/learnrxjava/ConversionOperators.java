package ru.kazakova_net.learnrxjava;

import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Эти операторы позволяют преобразовывать данные, которые генерирует Observable.
 */
public class ConversionOperators {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    public static void run() {
//        map();
        
        buffer();
    }
    
    /**
     * Оператор map преобразует все элементы последовательности.
     */
    private static void map() {
        // Объект Func1 - это функция, через которую будет проходить каждый элемент последовательности.
        // Этот объект требует от нас указания входного и выходного типов.
        Func1<String, Integer> stringToInteger = new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Integer.parseInt(s);
            }
        };
        
        // create observable
        Observable<Integer> observable = Observable
                .from(new String[]{"1", "2", "3", "4", "5", "6", "a"})
                .map(stringToInteger);
        
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
     * Оператор buffer собирает элементы и по мере накопления заданного кол-ва отправляет их дальше одним пакетом.
     * Создадим Observable из 8 чисел, и добавим к нему буфер с количеством элементов = 3.
     */
    private static void buffer() {
        // create observable
        Observable<List<Integer>> observable = Observable
                .from(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8})
                .buffer(3);
        
        // create observer
        Observer<List<Integer>> observer = new Observer<List<Integer>>() {
            @Override
            public void onCompleted() {
                Log.d(LOG_TAG, "onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "onError: " + e);
                
            }
            
            @Override
            public void onNext(List<Integer> s) {
                Log.d(LOG_TAG, "onNext: " + s);
            }
        };
        
        // subscribe
        observable.subscribe(observer);
    }
}
