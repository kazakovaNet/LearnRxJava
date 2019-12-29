package ru.kazakova_net.learnrxjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Операторы создания
//        CreationOperators.run();
        
        // Операторы преобразования
//        ConversionOperators.run();
        
        // Операторы фильтрации
//        FilteringOperators.run();
        
        // Операторы объединения
//        AggregationOperators.run();
        
        // Условные операторы
        ConditionOperators.run();
    }
}
