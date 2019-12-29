package com.example.android.hotandcoldobservable;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observables.ConnectableObservable;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        coldObservable();

//        hotObservable();

//        replay();
        
        refCount();
    }
    
    /**
     * Т.е. просто создание Observable не приводит ни к чему.
     * Этот созданный Observable ничего не будет делать.
     * Он начнет работу только когда кто-либо подпишется на него.
     * И для каждого нового подписчика он будет начинать работу заново,
     * независимо от предыдущих подписчиков.
     * <p>
     * Такой Observable называется Cold Observable.
     */
    private void coldObservable() {
        final Observer<Long> observer1 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer1 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer1 onNext value = " + aLong);
            }
        };
        
        final Observer<Long> observer2 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer2 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer2 onNext value = " + aLong);
            }
        };
        
        Log.d(TAG, "observable create");
        
        final Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS).take(5);
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer1 subscribe");
            
            observable.subscribe(observer1);
        }, 3000);
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer2 subscribe");
            
            observable.subscribe(observer2);
        }, 5500);
    }
    
    /**
     * Hot Observable генерирует данные независимо от подписчиков.
     * И все подписчики получают одни и те же данные в одно и то же время.
     */
    private void hotObservable() {
        final Observer<Long> observer1 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer1 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer1 onNext value = " + aLong);
            }
        };
        
        final Observer<Long> observer2 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer2 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer2 onNext value = " + aLong);
            }
        };
        
        final ConnectableObservable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(6)
                .publish();
        
        Log.d(TAG, "observer1 subscribe");
        observable.subscribe(observer1);
        
        Log.d(TAG, "observable connect");
        observable.connect();
        
        getWindow().getDecorView().postDelayed(() -> {
//            Log.d(TAG, "observer1 subscribe");

//            observable.subscribe(observer1);
        }, 2500);
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer2 subscribe");
            
            observable.subscribe(observer2);
        }, 5500);
    }
    
    /**
     * Hot Observable кэширует данные и отправляет их всем новым подписчикам,
     * чтобы они ничего не пропустили.
     * Метод replay имеет различные варианты,
     * в которых вы можете указать кол-во хранимых элементов или время их хранения.
     */
    private void replay() {
        final Observer<Long> observer1 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer1 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer1 onNext value = " + aLong);
            }
        };
        
        final Observer<Long> observer2 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer2 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer2 onNext value = " + aLong);
            }
        };
        
        final ConnectableObservable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(6)
                .replay();
        
        Log.d(TAG, "observable connect");
        observable.connect();
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer1 subscribe");
            
            observable.subscribe(observer1);
        }, 2500);
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer2 subscribe");
            
            observable.subscribe(observer2);
        }, 4500);
    }
    
    /**
     * Для ConnectableObservable существует возможность сделать так,
     * чтобы он начинал работать при первом появившемся подписчике,
     * и заканчивал после того, как отпишется последний.
     */
    private void refCount() {
        final Subscription[] subscriptions = new Subscription[1];
        
        final Observer<Long> observer1 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer1 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer1 onNext value = " + aLong);
            }
        };
        
        final Observer<Long> observer2 = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "observer2 onCompleted");
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
            
            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "observer2 onNext value = " + aLong);
            }
        };
        
        final Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(6)
                .publish()
                .refCount();
        
        Log.d(TAG, "observable create");
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer1 subscribe");
            
            subscriptions[0] = observable.subscribe(observer1);
        }, 1500);
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer2 subscribe");
    
            // TODO: 2019-12-29 ArrayIndexOutOfBoundsException
            subscriptions[1] = observable.subscribe(observer2);
        }, 3000);
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer1 unsubscribe");
            
            subscriptions[0].unsubscribe();
        }, 5000);
        
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer2 unsubscribe");
            
            subscriptions[1].unsubscribe();
        }, 6000);
    
        getWindow().getDecorView().postDelayed(() -> {
            Log.d(TAG, "observer1 subscribe");
    
            observable.subscribe(observer1);
        }, 6500);
    }
}
