package ru.kazakova_net.learnrxjava;


import android.util.Log;

import rx.Observable;

/**
 * Эти операторы позволяют проверять условие для элементов данных
 */
public class ConditionOperators {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    public static void run() {
//        takeUntil();
        
        all();
    }
    
    /**
     * Оператор takeUntil будет брать элементы пока не попадется элемент,
     * удовлетворяющий определенному условию.
     */
    private static void takeUntil() {
        Observable.from(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8})
                .takeUntil(i -> i == 5)
                .subscribe(i -> Log.d(LOG_TAG, String.valueOf(i)));
    }
    
    /**
     * Оператор all позволяет узнать все ли элементы удовлетворяют указанному условию.
     */
    private static void all() {
        Observable.from(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8})
                .all(i -> i < 10)
                .subscribe(b -> Log.d(LOG_TAG, String.valueOf(b)), e -> {
                }, () -> Log.d(LOG_TAG, "onCompleted"));
    }
}
