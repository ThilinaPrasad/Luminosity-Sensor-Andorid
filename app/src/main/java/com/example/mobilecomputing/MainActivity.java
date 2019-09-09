package com.example.mobilecomputing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private View root;
    private TextView textField;
    private Button action_btn;
    private boolean status;
    private float maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = false;

        // initialize views
        root = findViewById(R.id.root);
        textField = (TextView)findViewById(R.id.textField);
        action_btn = (Button)findViewById(R.id.action_btn);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(sensor==null){
            Toast.makeText(this, "This device doesn't have light sensor", Toast.LENGTH_SHORT).show();
            finish();
        }

        maxValue = sensor.getMaximumRange();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
//                int backgroundColorVal = (int) (1000000f * value / maxValue);
//                root.setBackgroundColor(Color.rgb(0,0,backgroundColorVal));
                textField.setText(Float.toString(value));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        action_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status){
                    status = false;
                    sensorManager.unregisterListener(sensorEventListener);
                    action_btn.setText("RESUME");
                    action_btn.setBackgroundColor(0xff99cc00);
                }else{
                    status = true;
                    sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
                    action_btn.setText("PAUSE");
                    action_btn.setBackgroundColor(0xffff4444);
                }
            }
        });
    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
//    }

//    @Override
//    protected void onPause(){
//        super.onPause();
//        sensorManager.unregisterListener(sensorEventListener);
//    }
}
