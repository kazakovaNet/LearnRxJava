package ru.kazakova_net.learnrxjava;

import android.util.Log;

import rx.Observable;

/**
 * Эти операторы позволяют объединять данные
 */
public class AggregationOperators {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    public static void run() {
//        merge();
        
        zip();
    }
    
    /**
     * Оператор merge объединит элементы из двух Observable в один Observable
     */
    private static void merge() {
        Observable.from(new Integer[]{1, 2, 3})
                .mergeWith(Observable.from(new Integer[]{6, 7, 8, 9}))
                .subscribe(s -> Log.d(LOG_TAG, String.valueOf(s)));
    }
    
    /**
     * Оператор zip попарно сопоставит элементы из двух Observable.
     * Из каждой пары элементов с помощью функции будет получен один элемент,
     * который будет добавлен в итоговый Observable.
     */
    private static void zip() {
        Observable.from(new Integer[]{1, 2, 3})
                .zipWith(Observable.from(new String[]{"One", "Two", "Three"}), (integer, s) -> s + ": " + integer)
                .subscribe(s -> Log.d(LOG_TAG, String.valueOf(s)));
    }
}
