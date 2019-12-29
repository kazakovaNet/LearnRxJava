package com.example.android.subscription;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    Observable<Long> mLongObservable = Observable.interval(1, TimeUnit.SECONDS);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testSubscription();

//        compositeSubscription();
        
        customObserver();
    }
    
    private void testSubscription() {
        Subscription subscription = mLongObservable
                .subscribe(l -> Log.d(TAG, "onNext: " + l));
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "unsubscribe");
            
            subscription.unsubscribe();
        }, 4500);
    }
    
    private void compositeSubscription() {
        Subscription subscription = mLongObservable
                .subscribe(l -> Log.d(TAG, "onNext: " + l));
        
        Subscription subscription1 = mLongObservable
                .subscribe(l -> Log.d(TAG, "onNext: " + l));
        
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(subscription);
        compositeSubscription.add(subscription1);
        
        Log.d(TAG, "subscription1 is unsubscribed " + subscription.isUnsubscribed());
        Log.d(TAG, "subscription2 is unsubscribed " + subscription1.isUnsubscribed());
        
        Log.d(TAG, "unsubscribe CompositeSubscription");
        compositeSubscription.unsubscribe();
        
        Log.d(TAG, "subscription1 is unsubscribed " + subscription.isUnsubscribed());
        Log.d(TAG, "subscription2 is unsubscribed " + subscription1.isUnsubscribed());
    }
    
    private void customObserver() {
        Subscription subscription = Observable
                .create((Observable.OnSubscribe<Integer>) subscriber -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
    
                        if (subscriber.isUnsubscribed()){
                            return;
                        }
                        
                        subscriber.onNext(i);
                    }
                    
                    if (subscriber.isUnsubscribed()){
                        return;
                    }
                    
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .subscribe(
                        i -> Log.d(TAG, "onNext: " + i),
                        e -> Log.d(TAG, "onError: " + e),
                        () -> Log.d(TAG, "onCompleted")
                );
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "unsubscribe");
            subscription.unsubscribe();
        }, 4500);
    }
}
